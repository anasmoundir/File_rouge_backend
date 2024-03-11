package com.example.demo.security.config.ConfigService;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.Collections;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        String roleName = user.getUserRole().getRoleName();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(!user.isEnabled())
                .build();
    }
}
