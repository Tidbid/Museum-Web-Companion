package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.UserRegistrationDto;
import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import com.romanov.rksp.museum.service.AppUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
        appUserService.saveUser(userRegistrationDto);
        return "redirect:/museum/security/user/registration?success";
    }

    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        //tf is uri
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

    @PostMapping("/role/assign")
    public ResponseEntity<?> assignRole(@RequestBody RoleToUserForm form) {
        appUserService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
