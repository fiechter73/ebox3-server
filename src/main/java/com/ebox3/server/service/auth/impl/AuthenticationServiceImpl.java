package com.ebox3.server.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;
import com.ebox3.server.dao.request.SigninRequest;
import com.ebox3.server.dao.response.JwtAuthenticationResponse;
import com.ebox3.server.service.auth.AuthenticationService;
import com.ebox3.server.service.auth.JwtService;
import com.ebox3.server.service.auth.UserAuthService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public JwtAuthenticationResponse signin(SigninRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		final UserDetails user = userAuthService.userDetailsService().loadUserByUsername(request.getUsername());
		String jwt = jwtService.generateToken(user);
		JwtAuthenticationResponse jwtAResponse = new JwtAuthenticationResponse();
		jwtAResponse.setToken(jwt);
		return jwtAResponse;
	}
}
