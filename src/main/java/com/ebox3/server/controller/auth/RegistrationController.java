package com.ebox3.server.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.dao.request.SignUpRequest;
import com.ebox3.server.dao.response.JwtAuthenticationResponse;
import com.ebox3.server.service.auth.RegistrationService;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(registrationService.signup(request));
	}
}
