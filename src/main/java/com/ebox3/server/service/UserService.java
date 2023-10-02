package com.ebox3.server.service;

import java.util.Map;

import com.ebox3.server.model.dto.UserDTO;

public interface UserService {

	public Map<String, Object> getAllUsers(String search, int page, int size);

	public UserDTO update(Long id, UserDTO userDTO);

	public UserDTO getUserById(Long id);

	public Long deleteUser(Long id);

}
