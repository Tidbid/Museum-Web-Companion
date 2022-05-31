package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.dto.UserRegistrationDto;
import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import com.romanov.rksp.museum.repository.AppUserRepo;
import com.romanov.rksp.museum.repository.RoleRepo;
import com.romanov.rksp.museum.util.RolesToAuthorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByUsername(username);
        if (user == null) {
            log.error("User with username: {} not found", username);
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User with username: {} queried and found", username);
        }
        return new User(
                user.getUsername(),
                user.getPassword(),
                RolesToAuthorities.map(user.getRoles())
        );
    }

    @Override
    public AppUser getById(Long user_id) {
        return appUserRepo.getById(user_id);
    }

    @Override
    public Boolean validateUsername(String username) {
        return appUserRepo.findByUsername(username) == null;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepo.save(user);
    }

    @Override
    public AppUser saveUser(UserRegistrationDto userRegistrationDto) {
        AppUser user = new AppUser(userRegistrationDto);
        log.info("Saving a new registered user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        addRoleToUser(appUserRepo.save(user).getUsername(), "USER_ROLE");
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        log.info("Adding role {} to a user {}", role.getName(), user.getUsername());
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {} from the database", username);
        return appUserRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users from the database");
        return appUserRepo.findAll();
    }

    @Override
    public Collection<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Collection<Role> getRemainingRoles(Collection<Role> currentRoles) {
        Collection<Role> roles = getRoles();
        roles.removeAll(currentRoles);
        return roles;
    }

    @Override
    public AppUser updateUser(AppUser user) {
        AppUser ret = appUserRepo.findByUsername(user.getUsername());
        ret.setName(user.getName());
        ret.setPatronymic(user.getPatronymic());
        ret.setSurname(user.getSurname());
        ret.setRoles(user.getRoles());
        return ret;
    }
}
