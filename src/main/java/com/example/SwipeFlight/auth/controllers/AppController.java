package com.example.SwipeFlight.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	@GetMapping("/")
	public String viewHomePage() {
		return "redirect:/home";
	}

	@GetMapping("/template")
	public String viewTemplatePage() {
		return "template";
	}

	@GetMapping("/aboutUs")
	public String viewAboutUsPage() {
		return "aboutUs";
	}

	@GetMapping("/error")
	public String viewErrorPage() {
		return "error";
	}
}
