package com.learningspring.crm.data.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class User {

    private Long id;
    private String username;
    private String password;
    private Collection<String> authorities;

    public User() {
    }

    public User(String username, String password, String authorities) {
        this(username,
                password,
                Arrays.asList(authorities.split(",")));
    }

    public User(String username, String password, Collection<String> authorities) {
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
