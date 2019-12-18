package com.UserManagement.user.security;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.google.gson.Gson;

@ControllerAdvice
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	Gson gsonObj = new Gson();
	Map<String, String> map = new LinkedHashMap<String, String>();

	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		map.put("success", "false");
		map.put("message", "Error in authentication");
		String jsonText = gsonObj.toJson(map);

		jsonText = jsonText.replace("\\", "");

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.sendError(HttpServletResponse.SC_FORBIDDEN, jsonText);

	}

}
