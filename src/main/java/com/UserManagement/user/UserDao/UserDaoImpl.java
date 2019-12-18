package com.UserManagement.user.UserDao;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.UserManagement.user.UserBean.Token;
import com.UserManagement.user.UserBean.User;
import com.UserManagement.user.UserRepository.TokenRepository;
import com.UserManagement.user.UserRepository.UserRepository;
import com.UserManagement.user.security.Iconstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Repository("repo")
public class UserDaoImpl implements IUserDao {

	@Autowired
	private UserRepository repository;

	@Autowired
	private TokenRepository tokenrepo;

	@Override
	public ResponseEntity<String> createUser(String authorization, User user) throws ServletException {
		
		String jwttokenfromheader = authorization.split(" ")[1];
		String body = jwttokenfromheader.split("\\.")[1];
		byte[] decoded_byte = Base64.getDecoder().decode(body);
		String decoded_string = new String(decoded_byte);
		ObjectMapper mapper = new ObjectMapper();
		Map data = null;
		try {
			data = mapper.readValue(decoded_string, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data.toString());
		Map<String, String> map = new LinkedHashMap<String, String>();
		Gson gsonObj = new Gson();
		
		if(data.get("rol").equals("admin")){

			if (repository.findByUsername(user.getUsername()).isPresent()) {
				map.put("success", "false");
				map.put("message", "User already exists");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			} else {
				repository.save(user);
				map.put("success", "true");
				map.put("message", "User added");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			}
		}
		else{
			map.put("success", "false");
			map.put("message", "Only admin is permitted to perform this operation.");
			map.put("status code", HttpStatus.FORBIDDEN.value()+"");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}

		

	}

	@Override
	public List<User> getAllUsers(String authorization) throws ServletException {
		//System.out.println("lalala - " + authorization);
		return repository.findAll();
	}

	@Override
	public ResponseEntity<String> getOneUser(String authorization, String username)
			throws ServletException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		Gson gsonObj = new Gson();

		if (repository.findByUsername(username).isPresent()) {

			Optional<User> user_optional = repository.findByUsername(username);
			User my_user = user_optional.get();
			map.put("id", my_user.getId());
			map.put("username", my_user.getUsername());
			map.put("password", my_user.getPassword());
			map.put("role", my_user.getRole());
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		} else {
			map.put("success", "false");
			map.put("message", "User does not exist");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<String> deleteUser(String authorization, String username)
			throws ServletException {

		String jwttokenfromheader = authorization.split(" ")[1];
		String body = jwttokenfromheader.split("\\.")[1];
		byte[] decoded_byte = Base64.getDecoder().decode(body);
		String decoded_string = new String(decoded_byte);
		ObjectMapper mapper = new ObjectMapper();
		Map data = null;
		try {
			data = mapper.readValue(decoded_string, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data.toString());
		Map<String, String> map = new LinkedHashMap<String, String>();
		Gson gsonObj = new Gson();
		
		if(data.get("rol").equals("admin")){
			
			if (repository.findByUsername(username).isPresent()) {
				repository.deleteByUsername(username);
				map.put("success", "true");
				map.put("message", "User deleted");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			} else {
				map.put("success", "false");
				map.put("message", "User does not exist");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			}
		}
		else{
			map.put("success", "false");
			map.put("message", "Only admin is permitted to perform this operation.");
			map.put("status code", HttpStatus.FORBIDDEN.value()+"");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}
		
		

	}

	@Override
	public ResponseEntity<String> deleteAllUsers(String authorization) throws ServletException {

		String jwttokenfromheader = authorization.split(" ")[1];
		String body = jwttokenfromheader.split("\\.")[1];
		byte[] decoded_byte = Base64.getDecoder().decode(body);
		String decoded_string = new String(decoded_byte);
		ObjectMapper mapper = new ObjectMapper();
		Map data = null;
		try {
			data = mapper.readValue(decoded_string, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data.toString());
		Map<String, String> map = new LinkedHashMap<String, String>();
		Gson gsonObj = new Gson();
		
		if(data.get("rol").equals("admin")){
			
			repository.deleteAll();
			map.put("success", "true");
			map.put("message", "All users deleted");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}
		else{
			map.put("success", "false");
			map.put("message", "Only admin is permitted to perform this operation.");
			map.put("status code", HttpStatus.FORBIDDEN.value()+"");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}
		
		

	}

	@Override
	public ResponseEntity<String> updateuser(String authorization, User user) throws ServletException {

		String jwttokenfromheader = authorization.split(" ")[1];
		String body = jwttokenfromheader.split("\\.")[1];
		byte[] decoded_byte = Base64.getDecoder().decode(body);
		String decoded_string = new String(decoded_byte);
		ObjectMapper mapper = new ObjectMapper();
		Map data = null;
		try {
			data = mapper.readValue(decoded_string, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data.toString());
		Map<String, String> map = new LinkedHashMap<String, String>();
		Gson gsonObj = new Gson();
		
		if(data.get("rol").equals("admin")){
			
			Optional<User> s = repository.findByUsername(user.getUsername());
			if (!s.isPresent()) {
				map.put("success", "false");
				map.put("message", "User does not exist");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			} else {
				user.setId(s.get().getId());
				repository.save(user);
				map.put("success", "true");
				map.put("message", "User updated");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			}
		}
		else{
			map.put("success", "false");
			map.put("message", "Only admin is permitted to perform this operation.");
			map.put("status code", HttpStatus.FORBIDDEN.value()+"");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}
		
		

	}

	@Override
	public ResponseEntity<String> login(User user) throws ServletException {

		String jwttoken = "";
		
		Gson gsonObject = new Gson();
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		
		Optional<User> user_db = repository.findByUsername(user
				.getUsername());
		User userdb = user_db.get();
		if (!user_db.isPresent()) {
			map1.put("success", "false");
			map1.put("message", "User does not exist");
			String jsonText = gsonObject.toJson(map1);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		}

		if (tokenrepo.findById(user.getUsername()).isPresent()) {
			Optional<Token> token_optional = tokenrepo.findById(user
					.getUsername());
			Token token = token_optional.get();
			String jwttokenfromrepo = token.getToken();
			String body = jwttokenfromrepo.split("\\.")[1];
			byte[] decoded_byte = Base64.getDecoder().decode(body);
			String decoded_string = new String(decoded_byte);
			ObjectMapper mapper = new ObjectMapper();
			Map data = null;
			try {
				data = mapper.readValue(decoded_string, Map.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(data.toString());
			long exp = (long) data.get("exp");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			
			String CurrentTime = dateFormat.format(new Date().getTime());
			System.out.println("Current Time - " + CurrentTime);
			
			String ExpiryTime = dateFormat.format(exp);
			System.out.println("Expiry time - " + ExpiryTime);
			
			if (exp > (new Date().getTime())) {
				jwttoken = token.getToken();
				System.out.println(jwttoken);
			} else {
				Map<String, Object> claims = new HashMap<String, Object>();
				claims.put("usr", user.getUsername());
				claims.put("sub", "Authentication token");
				claims.put("iss", Iconstants.ISSUER);
				claims.put("rol", userdb.getRole());
				claims.put(
						"iat",
						LocalDateTime.now().format(
								DateTimeFormatter
										.ofPattern("yyyy-MM-dd HH:mm:ss")));
				claims.put("exp", (new Date().getTime()) + 10800000);

				jwttoken = Jwts
						.builder()
						.setClaims(claims)
						.signWith(SignatureAlgorithm.HS512,
								Iconstants.SECRET_KEY).compact();
				System.out
						.println("Returning the following token to the user= "
								+ jwttoken);
				Token tokeninfo = new Token(user.getUsername(), jwttoken);
				tokenrepo.save(tokeninfo);
			}

		} else {

			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("usr", user.getUsername());
			claims.put("sub", "Authentication token");
			claims.put("iss", Iconstants.ISSUER);
			claims.put("rol", userdb.getRole());
			claims.put(
					"iat",
					LocalDateTime.now().format(
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			claims.put("exp", (new Date().getTime()) + 10800000);

			jwttoken = Jwts.builder().setClaims(claims)
					.signWith(SignatureAlgorithm.HS512, Iconstants.SECRET_KEY)
					.compact();
			System.out.println("Returning the following token to the user= "
					+ jwttoken);
			Token tokeninfo = new Token(user.getUsername(), jwttoken);
			tokenrepo.save(tokeninfo);
		}

		Gson gsonObj = new Gson();
		Optional<User> user_optional = repository.findByUsername(user
				.getUsername());
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (!user_optional.isPresent()) {
			map.put("success", "false");
			map.put("message", "User does not exist");
			String jsonText = gsonObj.toJson(map);
			return new ResponseEntity<String>(jsonText, HttpStatus.OK);
		} else {
			User my_user = user_optional.get();
			if (my_user.getPassword().equals(user.getPassword())) {
				map.put("success", "true");
				map.put("message", "Login successful");
				map.put("authorization", jwttoken);
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			} else {
				map.put("success", "false");
				map.put("message", "password incorrect");
				String jsonText = gsonObj.toJson(map);
				return new ResponseEntity<String>(jsonText, HttpStatus.OK);
			}
		}

	}

	@Override
	public ResponseEntity<String> signout(String username)
			throws ServletException {
		if (tokenrepo.findById(username).isPresent()) {
			tokenrepo.deleteById(username);
		}
		Gson gsonObj = new Gson();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("success", "true");
		map.put("message", "Signout successful");
		String jsonText = gsonObj.toJson(map);
		return new ResponseEntity<String>(jsonText, HttpStatus.OK);
	}

}
