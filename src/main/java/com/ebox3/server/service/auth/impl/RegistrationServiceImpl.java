package com.ebox3.server.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ebox3.server.dao.request.SignUpRequest;
import com.ebox3.server.dao.response.JwtAuthenticationResponse;
import com.ebox3.server.model.User;
import com.ebox3.server.repo.UserRepository;
import com.ebox3.server.service.auth.JwtService;
import com.ebox3.server.service.auth.RegistrationService;
import com.ebox3.server.service.auth.UserAuthService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;

	@Override
	public JwtAuthenticationResponse signup(SignUpRequest request) {

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		userRepository.save(user);

		final UserDetails newUser = userAuthService.userDetailsService().loadUserByUsername(request.getUsername());
		String jwt = jwtService.generateToken(newUser);
		JwtAuthenticationResponse jwtAResponse = new JwtAuthenticationResponse();
		jwtAResponse.setToken(jwt);
		return jwtAResponse;
	}

}
