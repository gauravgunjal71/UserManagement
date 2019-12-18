package com.UserManagement.user.UserDao;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.http.ResponseEntity;

import com.UserManagement.user.UserBean.User;

public interface IUserDao {

	public ResponseEntity<String> createUser(String authorization, User user) throws ServletException;

	public List<User> getAllUsers(String authorization) throws ServletException;

	public ResponseEntity<String> getOneUser(String authorization, String username) throws ServletException;

	public ResponseEntity<String> deleteUser(String authorization, String username) throws ServletException;
	
	public ResponseEntity<String> deleteAllUsers(String authorization) throws ServletException;
	
	public ResponseEntity<String> updateuser(String authorization, User user) throws ServletException;
	
	public ResponseEntity<String> login(User user) throws ServletException;
	
	ResponseEntity<String> signout(String username) throws ServletException;

}
