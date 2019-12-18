package com.UserManagement.user.UserRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.UserManagement.user.UserBean.Token;

public interface TokenRepository extends MongoRepository<Token, String>{

	

}
