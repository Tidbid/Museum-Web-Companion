package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.dto.UserRegistrationDto;
import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {
    AppUser saveUser(AppUser user);

    AppUser saveUser(UserRegistrationDto userRegistrationDto);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
