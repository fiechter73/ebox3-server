package com.ebox3.server.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.dao.request.SigninRequest;
import com.ebox3.server.dao.response.JwtAuthenticationResponse;
import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.model.dto.MessageDTO;
import com.ebox3.server.service.SalesService;
import com.ebox3.server.service.auth.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private SalesService salesService;

	@PostMapping("/authenticate")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) throws Exception {
		return ResponseEntity.ok(authenticationService.signin(request));

	}

	@GetMapping("/sale")
	public @ResponseBody Iterable<EboxDTO> getFreeObjects() {
		return salesService.getFreeObjects();

	}

	@GetMapping("/email/send")
	public ResponseEntity<MessageDTO> sendEMail(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("tel") String tel, @RequestParam("nachricht") String nachricht,
			@RequestParam("ids") String ids) {
		return ResponseEntity.ok(salesService.sendEMail(name, email, tel, nachricht, ids));
	}

}
