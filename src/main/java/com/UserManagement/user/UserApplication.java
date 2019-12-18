package com.UserManagement.user;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@Configuration
//@EnableSwagger2
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	
//	@Bean
//    public Docket api() { 
//        return new Docket(DocumentationType.SWAGGER_2)
//        .select()
//        .apis(RequestHandlerSelectors.basePackage("com.UserManagement.user"))
//        .build();
//    }
	
//	@Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
//                .securityContexts(Collections.singletonList(
//                        SecurityContext.builder()
//                                .securityReferences(
//                                        Collections.singletonList(SecurityReference.builder()
//                                                .reference("JWT")
//                                                .scopes(new AuthorizationScope[0])
//                                                .build()
//                                        )
//                                )
//                                .build())
//                )
//                .select()
//                .apis(RequestHandlerSelectors
//                        .basePackage("com.UserManagement.user"))
//                .paths(PathSelectors.regex("/.*"))
//                .build();
//    }
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
	        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
