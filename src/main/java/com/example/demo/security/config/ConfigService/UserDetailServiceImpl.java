package com.example.demo.security.config.ConfigService;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.Collections;

@Service
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
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleName.toUpperCase());
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(!user.isEnabled())
                .build();
    }
}
