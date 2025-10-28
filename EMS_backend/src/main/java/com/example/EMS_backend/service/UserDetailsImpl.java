package com.example.EMS_backend.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.EMS_backend.dao.entity.RoleEntity;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private String name;
    private String password;
    private List<SimpleGrantedAuthority> allRoles;

    public UserDetailsImpl(String name, String password, List<RoleEntity> allRoles) {
        this.name = name;
        this.password = password;
        this.allRoles = allRoles.stream().map((role)-> new SimpleGrantedAuthority(role.getRoleName())).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return allRoles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
