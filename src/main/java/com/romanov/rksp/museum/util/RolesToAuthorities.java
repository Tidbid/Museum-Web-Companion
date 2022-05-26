package com.romanov.rksp.museum.util;

import com.romanov.rksp.museum.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class RolesToAuthorities {
    public static Collection<? extends GrantedAuthority> map(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }
}
