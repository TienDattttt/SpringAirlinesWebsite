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
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String viewLoginPage(ModelMap model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login/ProcessUserLogin")
    public String processUserLogin(@ModelAttribute("user") User user, ModelMap model,
            BindingResult result,
            HttpServletRequest request) {

        result = userService.validateUserLogin(user, result);

        if (result.hasErrors())
            return "login";

        if (authService.authenticateUser(user.getUserName(), user.getPassword())) {
            authService.manageLoginSuccess(user.getUserName(), request);
            return "redirect:/home";
        }

        authService.manageLoginFailureOrLogout(request);

        return "login";
    }

}
