package com.ebox3.server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.model.dto.UserDTO;
import com.ebox3.server.service.UserService;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/find")
	public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(required = false) String search,
			@RequestParam("page") int page, @RequestParam("size") int size) {
		return ResponseEntity.ok(userService.getAllUsers(search, page, size));
	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, @Validated @RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(userService.update(id, userDTO));
	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PreAuthorize("hasRole('ROLE_admin')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}
}
