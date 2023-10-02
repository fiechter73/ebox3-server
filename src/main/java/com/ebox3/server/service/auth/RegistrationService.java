package com.ebox3.server.service.auth;

import com.ebox3.server.dao.request.SignUpRequest;
import com.ebox3.server.dao.response.JwtAuthenticationResponse;

public interface RegistrationService {

	public JwtAuthenticationResponse signup(SignUpRequest request);

}
