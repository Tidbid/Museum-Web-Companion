package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.UserRegistrationDto;
import com.romanov.rksp.museum.dto.UserRolesDto;
import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import com.romanov.rksp.museum.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/museum/security")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/user/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping("/user/registration")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto, Model model) {
        if (appUserService.validateUsername(userRegistrationDto.getUsername())) {
            appUserService.saveUser(userRegistrationDto);
            return "redirect:/museum/security/user/registration?success";
        }
        model.addAttribute("user", userRegistrationDto);
        return "registration";
    }

    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri =
                URI.create(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString()
                );
        return ResponseEntity.created(uri).body(appUserService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri =
                URI.create(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString()
                );
        return ResponseEntity.created(uri).body(appUserService.saveRole(role));
    }

    @GetMapping("/admin/user")
    public String updateUser(@RequestParam String username, Model model) {
        AppUser user = appUserService.getUser(username);
        model.addAttribute("userRolesDto", new UserRolesDto(user, user.getRoles()));
        model.addAttribute("remainingRoles", appUserService.getRemainingRoles(user.getRoles()));
        return "modify_form_user";
    }

    @GetMapping("/admin/all")
    public String getUsers(Model model) {
        model.addAttribute("users", appUserService.getUsers());
        return "all_users";
    }

    @PostMapping("/admin/save")
    public String modifyUser(@ModelAttribute("userRolesDto") UserRolesDto userRolesDto) {
        AppUser user = userRolesDto.getUser();
        user.setRoles(userRolesDto.getRolesToAdd());
        appUserService.updateUser(user);
        return "redirect:/museum/security/admin/all";
    }
}
