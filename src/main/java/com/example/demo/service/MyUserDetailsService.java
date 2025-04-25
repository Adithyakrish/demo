package com.example.demo.service;

// ✅ ADD THIS:
import org.springframework.security.core.userdetails.User;
import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRespository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AppUser> users = userRepo.findAllByUsername(username);
        if (users.size() != 1) {
            throw new UsernameNotFoundException("Expected exactly one user, found: " + users.size());
        }
        AppUser user = users.get(0);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
