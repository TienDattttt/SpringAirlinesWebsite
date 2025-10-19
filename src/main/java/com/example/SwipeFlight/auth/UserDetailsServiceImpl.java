package com.example.SwipeFlight.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.auth.user.User;
import com.example.SwipeFlight.auth.user.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getUserByUserName(username);

        if (user != null) {
            UserDetails ud = new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(), user.getAuthorities());
            return ud;
        }

        return null;
    }
}
