package com.example.demo;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.SplittableRandom;

public class User {
    public String name;
    public String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String username, String password, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
