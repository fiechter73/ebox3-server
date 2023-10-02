package com.ebox3.server.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.ebox3.server.model.User;
import com.ebox3.server.repo.UserRepository;
import com.ebox3.server.service.auth.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	
	@Autowired
	private UserRepository userRepository;
	
    @Override
    public UserDetailsService userDetailsService() {
    	
        return new UserDetailsService() {
        	
            @Override
            public UserDetails loadUserByUsername(String username) {
            	User user = userRepository.findByUsername(username)
            			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
				UserBuilder builder = null;
				if (user != null) {
					builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
					builder.password(user.getPassword());
					builder.roles(user.getRole());
				} else {
					throw new UsernameNotFoundException("User not found with username: " + username);
				}
				return builder.build();
            }
        };
    }
}
