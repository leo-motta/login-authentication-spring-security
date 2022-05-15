package com.leonardomotta.repository;

import org.springframework.data.repository.CrudRepository;

import com.leonardomotta.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByUsername(String username);
}
