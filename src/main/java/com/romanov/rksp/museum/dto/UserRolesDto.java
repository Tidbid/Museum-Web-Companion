package com.romanov.rksp.museum.dto;

import com.romanov.rksp.museum.model.AppUser;
import com.romanov.rksp.museum.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDto {
    private AppUser user;
    private Collection<Role> rolesToAdd;
}
