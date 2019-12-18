package com.UserManagement.user.UserBean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
public class Token {

	@Id
	String username;
	String token;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Token(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}
	public Token() {
		super();
	}
	@Override
	public String toString() {
		return "Token [username=" + username + ", token=" + token + "]";
	}

	
}
