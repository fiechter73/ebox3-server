package com.ebox3.server.service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService {
	public  UserDetailsService userDetailsService();
}
