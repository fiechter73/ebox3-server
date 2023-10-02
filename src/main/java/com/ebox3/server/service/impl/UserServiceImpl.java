package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ebox3.server.exception.InternalServerException;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.User;
import com.ebox3.server.model.dto.UserDTO;
import com.ebox3.server.repo.UserRepository;
import com.ebox3.server.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Map<String, Object> getAllUsers(String search, int page, int size) {
		try {
			List<UserDTO> listOfUsers = new ArrayList<UserDTO>();
			List<UserDTO> listOfUsersSorted = new ArrayList<UserDTO>();
			Pageable paging = PageRequest.of(page, size, Sort.by("username"));
			Page<User> users = null;
			List<User> usersList = null;
			PagedListHolder<UserDTO> usersDTO = null;
			Map<String, Object> response = new HashMap<>();

			if (search == null) {

				users = userRepository.findAll(paging);

				users.forEach(user -> {
					UserDTO usDTO = mapper.map(user, UserDTO.class);
					listOfUsers.add(usDTO);
				});

				response.put("users", listOfUsers);
				response.put("currentPage", users.getNumber());
				response.put("totalItems", users.getTotalElements());
				response.put("totalPages", users.getTotalPages());

			} else {

				usersList = userRepository.findByUsernameContainingIgnoreCaseOrRoleContainingIgnoreCase(search, search);

				usersList.forEach(myUsers -> {
					Optional<User> user = userRepository.findById(myUsers.getId());
					UserDTO usDTO = mapper.map(user.get(), UserDTO.class);
					listOfUsers.add(usDTO);

				});
				listOfUsersSorted = listOfUsers.stream().sorted(Comparator.comparing(UserDTO::getUsername))
						.collect(Collectors.toList());

				usersDTO = new PagedListHolder<UserDTO>(listOfUsersSorted);
				usersDTO.setPageSize(size);
				usersDTO.setPage(page);

				response.put("users", usersDTO.getPageList());
				response.put("currentPage", usersDTO.getPage());
				response.put("totalItems", usersDTO.getNrOfElements());
				response.put("totalPages", usersDTO.getPageCount());

			}

			return response;
		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}

	}

	@Override
	public UserDTO update(Long id, UserDTO userDTO) throws ResourceNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("User by id %d not found", id)));
		mapper.map(userDTO, user);
		return mapper.map(userRepository.save(user), UserDTO.class);
	}
	
	@Override
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("User by id %d not found", id)));

		UserDTO userDTO = mapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
			mapper.skip(UserDTO::setPassword);
		}).map(user);

		return userDTO;
	}

	@Override
	public Long deleteUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
		return id;
	}

}
