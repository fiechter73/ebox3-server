package com.ebox3.server.service.auth;

import com.ebox3.server.dao.request.SigninRequest;
import com.ebox3.server.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {

	public JwtAuthenticationResponse signin(SigninRequest request);

}
