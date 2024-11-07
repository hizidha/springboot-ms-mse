package com.devland.assignment.msems.authentication.model;

import com.devland.assignment.msems.applicationuser.model.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.lang.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
    @Getter
    private final Long id;

    @Getter
    private final String fullName;

    private final String username;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Long id, String fullName, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.authorities = Collections.emptyList();
    }

    public static UserDetails build(ApplicationUser applicationUser) {
        return new UserPrincipal(applicationUser.getId(), applicationUser.getFullName(),
                                 applicationUser.getUsername(), applicationUser.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}