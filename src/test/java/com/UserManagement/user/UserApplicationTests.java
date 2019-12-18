package com.UserManagement.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.assertj.core.util.Arrays;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

import com.UserManagement.user.UserBean.User;
import com.UserManagement.user.UserDao.UserDaoImpl;
import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserApplicationTests {

	private static UserDaoImpl mockUserDao = mock(UserDaoImpl.class);
	private static User defaultUser = new User("5dd21af42e0da1a5bc6b16bb" ,"admin", "admin", "crud");
	private static User user1 = new User("1", "admin123", "admin123", "crud");
	private static User user2 = new User("2", "admin456", "admin456", "crud");
	private static User userForUpdate = new User("admin123", "admin123", "crudupdated");
	private static User userForInvalidUpdate = new User("admin123456", "admin123", "crudupdated");
	private static User userForIncorrectPassword = new User("admin123", "admin12345", "crud");

	static Map<String, String> map = new LinkedHashMap<String, String>();
	static Gson gsonObj = new Gson();
	
	static String authorization;
	
	@BeforeClass
	public static void init() throws ServletException {

		// GET all users
		List<User> userlist = new ArrayList<User>();
		userlist.add(defaultUser);
		userlist.add(user1);
		userlist.add(user2);

		// GET one user
		map.put("id", user1.getId());
		map.put("username", user1.getUsername());
		map.put("password", user1.getPassword());
		map.put("subscriptions", user1.getRole());
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityGetUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// GET invalid user
		map.put("success", "false");
		map.put("message", "User does not exist");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityGetInvalidUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// CREATE user
		map.put("success", "true");
		map.put("message", "User added");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityCreateUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// CREATE user which is already exists
		map.put("success", "false");
		map.put("message", "User already exists");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityCreateExistedUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// UPDATE user
		map.put("success", "true");
		map.put("message", "User updated");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityUpdateUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// UPDATE invalid user
		map.put("success", "false");
		map.put("message", "User does not exist");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityUpdateInvalidUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// DELETE all users
		map.put("success", "true");
		map.put("message", "All users deleted");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityDeleteAllUsers = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// DELETE one user
		map.put("success", "true");
		map.put("message", "User deleted");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityDeleteOneUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// DELETE invalid user
		map.put("success", "false");
		map.put("message", "User does not exist");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityDeleteOneInvalidUser = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// LOGIN user
		map.put("success", "true");
		map.put("message", "Login successful");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityLogin = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// LOGIN with invalid username
		map.put("success", "false");
		map.put("message", "User does not exist");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityInvalidLogin = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		// LOGIN with incorrect password
		map.put("success", "false");
		map.put("message", "password incorrect");
		jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityIncorrectPasswordLogin = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);

		//GET
		when(mockUserDao.getAllUsers(authorization)).thenReturn(userlist);
		when(mockUserDao.getOneUser(authorization, user1.getUsername())).thenReturn(
				responseEntityGetUser);
		when(mockUserDao.getOneUser(authorization, "invalidUsername")).thenReturn(
				responseEntityGetInvalidUser);
		
		//CREATE
		when(mockUserDao.createUser(authorization, user1))
				.thenReturn(responseEntityCreateUser);
		when(mockUserDao.createUser(authorization, user2)).thenReturn(
				responseEntityCreateExistedUser);
		
		//UPDATE
		
		when(mockUserDao.updateuser(authorization, userForUpdate)).thenReturn(
				responseEntityUpdateUser);
		
		when(mockUserDao.updateuser(authorization, userForInvalidUpdate)).thenReturn(
				responseEntityUpdateInvalidUser);
		
		//DELETE
		when(mockUserDao.deleteUser(authorization, user1.getUsername())).thenReturn(
				responseEntityDeleteOneUser);
		when(mockUserDao.deleteUser(authorization, "InvalidUsername")).thenReturn(
				responseEntityDeleteOneInvalidUser);
		when(mockUserDao.deleteAllUsers(authorization)).thenReturn(
				responseEntityDeleteAllUsers);
		
		//LOGIN
		when(mockUserDao.login(user2)).thenReturn(responseEntityLogin);
		when(mockUserDao.login(userForInvalidUpdate)).thenReturn(
				responseEntityInvalidLogin);
		
		when(mockUserDao.login(userForIncorrectPassword)).thenReturn(
				responseEntityIncorrectPasswordLogin);

	}
	
	@Test
	public void testGetAllUsers() throws ServletException{
		List<User> userListActual = mockUserDao.getAllUsers(authorization);
		
		// GET all users
		List<User> userListExpected = new ArrayList<User>();
		userListExpected.add(defaultUser);
		userListExpected.add(user1);
		userListExpected.add(user2);
		
		assertEquals(userListExpected, userListActual);
	}
	
	@Test
	public void testGetOneUser() throws ServletException{
		ResponseEntity<String> responseEntityGetUserActual = mockUserDao.getOneUser(authorization, user1.getUsername());
		
		// GET one user
		map.put("id", user1.getId());
		map.put("username", user1.getUsername());
		map.put("password", user1.getPassword());
		map.put("subscriptions", user1.getRole());
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityGetUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityGetUserExpected, responseEntityGetUserActual);
	}
	
	@Test
	public void testGetInvalidUser() throws ServletException{
		ResponseEntity<String> responseEntityGetInvalidUserActual = mockUserDao.getOneUser(authorization, "invalidUsername");
		
		map.put("success", "false");
		map.put("message", "User does not exist");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityGetInvalidUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityGetInvalidUserExpected, responseEntityGetInvalidUserActual);
	}
	
	@Test
	public void testCreateUser() throws ServletException{
		ResponseEntity<String> responseEntityCreateUserActual = mockUserDao.createUser(authorization, user1);
		
		map.put("success", "true");
		map.put("message", "User added");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityCreateUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityCreateUserExpected, responseEntityCreateUserActual);
	}
	
	@Test
	public void testCreateExistedUser() throws ServletException{
		ResponseEntity<String> responseEntityCreateExistedUserActual = mockUserDao.createUser(authorization, user2);
		
		map.put("success", "false");
		map.put("message", "User already exists");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityCreateExistedUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityCreateExistedUserExpected, responseEntityCreateExistedUserActual);
	}
	
	@Test
	public void testUpdateUser() throws ServletException{
		ResponseEntity<String> responseEntityUpdateUserActual = mockUserDao.updateuser(authorization, userForUpdate);
		
		map.put("success", "true");
		map.put("message", "User updated");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityUpdateUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityUpdateUserExpected, responseEntityUpdateUserActual);
	}
	
	@Test
	public void testUpdateInvalidUser() throws ServletException{
		ResponseEntity<String> responseEntityUpdateInvalidUserActual = mockUserDao.updateuser(authorization, userForInvalidUpdate);
		
		map.put("success", "false");
		map.put("message", "User does not exist");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityUpdateInvalidUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityUpdateInvalidUserExpected, responseEntityUpdateInvalidUserActual);
	}
	
	@Test
	public void testDeleteAllUsers() throws ServletException{
		ResponseEntity<String> responseEntityDeleteAllUsersActual = mockUserDao.deleteAllUsers(authorization);
		
		map.put("success", "true");
		map.put("message", "All users deleted");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityDeleteAllUsersExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityDeleteAllUsersExpected, responseEntityDeleteAllUsersActual);
	}
	
	@Test
	public void testDeleteOneUser() throws ServletException{
		ResponseEntity<String> responseEntityDeleteOneUserActual = mockUserDao.deleteUser(authorization, user1.getUsername());
		
		map.put("success", "true");
		map.put("message", "User deleted");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityDeleteOneUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityDeleteOneUserExpected, responseEntityDeleteOneUserActual);
	}
	
	@Test
	public void testDeleteOneInvalidUser() throws ServletException{
		ResponseEntity<String> responseEntityDeleteOneInvalidUserActual = mockUserDao.deleteUser(authorization, "InvalidUsername");
		
		map.put("success", "false");
		map.put("message", "User does not exist");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityDeleteOneInvalidUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityDeleteOneInvalidUserExpected, responseEntityDeleteOneInvalidUserActual);
	}
	
	@Test
	public void testLoginUser() throws ServletException{
		ResponseEntity<String> responseEntityLoginUserActual = mockUserDao.login(user2);
		
		map.put("success", "true");
		map.put("message", "Login successful");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityLoginUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityLoginUserExpected, responseEntityLoginUserActual);
	}
	
	@Test
	public void testLoginInvalidUser() throws ServletException{
		ResponseEntity<String> responseEntityLoginInvalidUserActual = mockUserDao.login(userForInvalidUpdate);
		
		map.put("success", "false");
		map.put("message", "User does not exist");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityLoginInvalidUserExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityLoginInvalidUserExpected, responseEntityLoginInvalidUserActual);
	}
	
	@Test
	public void testLoginIncorrectPassword() throws ServletException{
		ResponseEntity<String> responseEntityLoginIncorrectPasswordActual = mockUserDao.login(userForIncorrectPassword);
		
		map.put("success", "false");
		map.put("message", "password incorrect");
		String jsonText = gsonObj.toJson(map);
		map.clear();
		ResponseEntity<String> responseEntityLoginIncorrectPasswordExpected = new ResponseEntity<String>(
				jsonText, HttpStatus.OK);
		
		assertEquals(responseEntityLoginIncorrectPasswordExpected, responseEntityLoginIncorrectPasswordExpected);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// private MockMvc mockMvc;
	//
	// @Autowired
	// private WebApplicationContext wac;
	//
	// @Before
	// public void setup() {
	// this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	// }
	//
	// @Test
	// public void contextLoads() {
	// }
	//
	// @Test
	// public void testAGetAllUsers() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders.get("/getusers").accept(
	// MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$", hasSize(1))).andDo(print());
	// }
	//
	// @Test
	// public void testBGetUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders.get("/getuser/admin").accept(
	// MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.username").exists())
	// .andExpect(jsonPath("$.password").exists())
	// .andExpect(jsonPath("$.username").value("admin"))
	// .andExpect(jsonPath("$.password").value("admin"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testCGetUserInvalidUsername() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders.get("/getuser/admin123").accept(
	// MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("false"))
	// .andExpect(jsonPath("$.message").value("User does not exist"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testDCreateUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .post("/createuser")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin123\", \"password\" : \"admin123\", \"subscriptions\" : \"crud\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("true"))
	// .andExpect(jsonPath("$.message").value("User added"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testECreateExistingUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .post("/createuser")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin123\", \"password\" : \"admin123\", \"subscriptions\" : \"crud\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("false"))
	// .andExpect(jsonPath("$.message").value("User already exists"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testFUpdateUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .put("/updateuser")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin123\", \"password\" : \"admin\", \"subscriptions\" : \"ud\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("true"))
	// .andExpect(jsonPath("$.message").value("User updated"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testGUpdateInvalidUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .put("/updateuser")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin12345\", \"password\" : \"admin\", \"subscriptions\" : \"ud\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("false"))
	// .andExpect(jsonPath("$.message").value("User does not exist"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testHDeleteUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders.delete("/deleteuser/admin123").accept(
	// MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("true"))
	// .andExpect(jsonPath("$.message").value("User deleted"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testIDeleteInvalidUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders.delete("/deleteuser/admin12345").accept(
	// MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("false"))
	// .andExpect(jsonPath("$.message").value("User does not exist"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testJDeleteAllUsers() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders.delete("/deleteusers").accept(
	// MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("true"))
	// .andExpect(jsonPath("$.message").value("All users deleted"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testKLoginUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .post("/login")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin\", \"password\" : \"admin\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("true"))
	// .andExpect(jsonPath("$.message").value("Login successful"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testLLoginInvalidUser() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .post("/login")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin12345\", \"password\" : \"admin\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("false"))
	// .andExpect(jsonPath("$.message").value("User does not exist"))
	// .andDo(print());
	// }
	//
	// @Test
	// public void testMLoginUserPasswordIncorrect() throws Exception {
	// mockMvc.perform(
	// MockMvcRequestBuilders
	// .post("/login")
	// .contentType(MediaType.APPLICATION_JSON)
	// .content(
	// "{\"username\" : \"admin\", \"password\" : \"admin123\" }")
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.success").exists())
	// .andExpect(jsonPath("$.message").exists())
	// .andExpect(jsonPath("$.success").value("false"))
	// .andExpect(jsonPath("$.message").value("password incorrect"))
	// .andDo(print());
	// }

}
