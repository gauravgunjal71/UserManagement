package com.UserManagement.user.UserRepository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.UserManagement.user.UserBean.User;

public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByUsername(String username);

	void deleteByUsername(String username);

}
