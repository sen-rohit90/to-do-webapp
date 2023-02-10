package com.todo.todowebapp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private LoginAuthenticationService authService;

	public LoginController(LoginAuthenticationService authService) {
		super();
		this.authService = authService;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String gotoLoginPage() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String gotoWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap model) {
		
		model.put("errorMessage", "Invalid credentials! Please try again.");
		if (authService.authenticate(name, password)) {

			model.put("name", name);
			model.put("password", password);
			return "welcome";
		}
		
		return "login";
	}
}
