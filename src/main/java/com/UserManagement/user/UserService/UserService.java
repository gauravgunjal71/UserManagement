package com.UserManagement.user.UserService;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.UserManagement.user.UserBean.User;
import com.UserManagement.user.UserDao.IUserDao;

@Service("userservice")
public class UserService {

	@Autowired
	private IUserDao userdao;
	
	public ResponseEntity<String> createUser(String authorization, User user)  throws ServletException{
		return userdao.createUser(authorization, user);
	}
	
	public List<User> getAllUsers(String authorization) throws ServletException{
		return userdao.getAllUsers(authorization);
	}
	
	public ResponseEntity<String> getOneUser(String authorization, String username) throws ServletException{
		return userdao.getOneUser(authorization, username);
	}
	
	public ResponseEntity<String> deleteUser(String authorization, String username) throws ServletException{
		return userdao.deleteUser(authorization, username);
	}
	
	public ResponseEntity<String> deleteAllUsers(String authorization) throws ServletException{
		return userdao.deleteAllUsers(authorization);
	}
	
	public ResponseEntity<String> updateuser(String authorization, User user)  throws ServletException{
		return userdao.updateuser(authorization, user);
	}
	
	public ResponseEntity<String> login(User user) throws ServletException{
		return userdao.login(user);
	}
	
	public ResponseEntity<String> signout(String username) throws ServletException{
		return userdao.signout(username);
	}

	
}
