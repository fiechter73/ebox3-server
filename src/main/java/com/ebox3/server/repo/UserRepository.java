package com.ebox3.server.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Page<UserRepository> findById(Long id, Pageable pageable);

	Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

	Page<User> findByRoleContainingIgnoreCase(String role, Pageable pageable);

	List<User> findByUsernameContainingIgnoreCaseOrRoleContainingIgnoreCase(String username, String role);

}
