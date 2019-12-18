package com.UserManagement.user.UserController;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.UserManagement.user.UserBean.User;
import com.UserManagement.user.UserDao.UserAlreadyExistsException;
import com.UserManagement.user.UserRepository.UserRepository;
import com.UserManagement.user.UserService.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userservice;
	
	@GetMapping(value = "/getusers")
	public List<User> getAllUsers(@RequestHeader("authorization") String authorization) throws ServletException {
		return userservice.getAllUsers(authorization);
	}

	@PostMapping(value = "/createuser")
	public ResponseEntity<String> createUser(@RequestHeader("authorization") String authorization, @RequestBody User user) throws ServletException{
		return userservice.createUser(authorization, user);
	}

	@GetMapping(value = "/getuser/{username}")
	public ResponseEntity<String> getOneUser(@RequestHeader("authorization") String authorization, @PathVariable String username) throws ServletException {
		return userservice.getOneUser(authorization, username);
	}

	@DeleteMapping(value = "/deleteuser/{username}")
	public ResponseEntity<String> deleteUser(@RequestHeader("authorization") String authorization, @PathVariable String username) throws ServletException {
		return userservice.deleteUser(authorization, username);
	}

	@DeleteMapping(value = "/deleteusers")
	public ResponseEntity<String> deleteUsers(@RequestHeader("authorization") String authorization) throws ServletException {
		return userservice.deleteAllUsers(authorization);
	}

	@PutMapping(value = "/updateuser")
	public ResponseEntity<String> updateUser(@RequestHeader("authorization") String authorization, @RequestBody User user) throws ServletException{
		return userservice.updateuser(authorization, user);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody User user) throws ServletException {
		return userservice.login(user);
	}
	
	@PostMapping("signout/{username}")
	public ResponseEntity<String> signout(@PathVariable String username) throws ServletException{
		return userservice.signout(username);
	}
}
