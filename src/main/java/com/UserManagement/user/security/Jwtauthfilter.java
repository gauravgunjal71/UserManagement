package com.UserManagement.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.UserManagement.user.UserRepository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Jwtauthfilter extends OncePerRequestFilter {

	@Autowired
	private TokenRepository tokenrepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException{

		// Fetching the authorization header from the request.
		String authenticationHeader = request.getHeader(Iconstants.HEADER);
		

		try {
			SecurityContext context = SecurityContextHolder.getContext();

			if (authenticationHeader != null
					&& authenticationHeader.startsWith("Bearer")) {
				
				String body = authenticationHeader.split("\\.")[1];
				byte[] decoded_byte = Base64.getDecoder().decode(body);
				String decoded_string = new String(decoded_byte);
				ObjectMapper mapper = new ObjectMapper();
				Map data = mapper.readValue(decoded_string, Map.class);
				System.out.println(data.toString());
				long exp = (long) data.get("exp");
				System.out.println(exp);
				try{
					//save
					if(exp < (new Date().getTime())){
						tokenrepo.deleteById((String) data.get("usr"));
						throw new Exception();
					}else{
						final String bearerTkn = authenticationHeader.replaceAll(
								Iconstants.BEARER_TOKEN, "");
						System.out
								.println("Following token is received from the protected url= "
										+ bearerTkn);

						try {
							// Parsing the jwt token.
							Jws<Claims> claims = Jwts.parser()
									.requireIssuer(Iconstants.ISSUER)
									.setSigningKey(Iconstants.SECRET_KEY)
									.parseClaimsJws(bearerTkn);

							// Obtaining the claims from the parsed jwt token.
							String user = (String) claims.getBody().get("usr");
							String roles = (String) claims.getBody().get("rol");
//							String role = "admin";

							// Creating the list of granted-authorities for the received
							// roles.
							List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
							for (String role : roles.split(","))
								authority.add(new SimpleGrantedAuthority(role));
//							authority.add(new SimpleGrantedAuthority(role));
							

							// Creating an authentication object using the claims.
							Myauthtoken authenticationTkn = new Myauthtoken(user, null,
									authority);
							// Storing the authentication object in the security
							// context.
							context.setAuthentication(authenticationTkn);
						} catch (SignatureException e) {
							throw new ServletException("Invalid token.");
						}	
					}
				}catch (Exception e){
					System.out.println("hello");
					
				}
				
			}

			filterChain.doFilter(request, response);
			context.setAuthentication(null);
		} catch (AuthenticationException ex) {
			throw new ServletException("Authentication exception.");
		}
	}
}
