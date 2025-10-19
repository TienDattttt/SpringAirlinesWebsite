package com.example.SwipeFlight.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SwipeFlight.auth.AuthService;
import com.example.SwipeFlight.auth.user.User;
import com.example.SwipeFlight.auth.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

	// dependency injections
	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;

	@GetMapping("/registration")
	public String viewRegisterPage(ModelMap model, HttpServletRequest request) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration/ProcessUserRegistration")
	public String processUserRegistration(@ModelAttribute("user") User user, ModelMap model,
			BindingResult result,
			HttpServletRequest request) {
		result = userService.validateUserRegistration(user, result);

		if (result.hasErrors())
			return "registration";

		userService.insertUser(user);
		authService.manageLoginSuccess(user.getUserName(), request);
		return "redirect:/home";
	}
}
