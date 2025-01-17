package com.example.Ecommerce.auth.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Ecommerce.user.entity.TypeUserEnum;

public class CustomUserDetails implements UserDetails {

    private String id;

    private String email;

    private TypeUserEnum tipo_user;

    private String password;

    public CustomUserDetails(String id, String email, TypeUserEnum tipo_user, String password) {

        this.id = id;
        this.email = email;
        this.tipo_user = tipo_user;
        this.password =password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + tipo_user.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }
    
}
