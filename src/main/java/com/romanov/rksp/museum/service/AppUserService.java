package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
