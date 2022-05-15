package com.leonardomotta.repository;

import org.springframework.data.repository.CrudRepository;

import com.leonardomotta.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByRole(String role);
}