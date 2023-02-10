package com.todo.todowebapp.login;

import org.springframework.stereotype.Service;

@Service
public class LoginAuthenticationService {
	public boolean authenticate(String username, String password) {
		
		boolean isValidUserName = username.equalsIgnoreCase("admin");
		boolean isValidPassword = password.equalsIgnoreCase("admin");
		
		return isValidUserName && isValidPassword;
		
	}
}
