package com.UserManagement.user.UserDao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends Exception {

	

	public UserAlreadyExistsException(String string) {
		super(string);
	}

}
