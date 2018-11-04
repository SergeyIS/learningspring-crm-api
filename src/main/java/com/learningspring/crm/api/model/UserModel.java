package com.learningspring.crm.api.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class UserModel {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String username;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String password;

    private Collection<String> authorities;

    public UserModel() {
    }

    public UserModel(String username, String password, String authorities) {
        this(username,
                password,
                Arrays.asList(authorities.split(",")));
    }

    public UserModel(String username, String password, Collection<String> authorities) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.authorities = Objects.requireNonNull(authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void addAuthority(String authority) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }

        authorities.add(authority);
    }
}
