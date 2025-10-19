package com.example.SwipeFlight.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SwipeFlight.auth.user.User;
import com.example.SwipeFlight.auth.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

	// dependency injections
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImp;
	@Autowired
	private UserService userService;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public boolean authenticateUser(String userName, String password) {

		UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(userName);

		if (userDetails == null)
			return false;

		return passwordEncoder.matches(password, userDetails.getPassword());
	}

	public void manageLoginSuccess(String userName, HttpServletRequest request) {
		try {

			User user = userService.getUserByUserName(userName);
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(),
					user.getPassword(), user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

			userService.updateLastSeen(userName);
		} catch (Exception e) {
			System.err.println(
					"*** Error -- AuthService -- login success error:\t user " + userName + "\t" + e.getMessage());
		}
	}

	public void manageLoginFailureOrLogout(HttpServletRequest request) {
		try {
			request.getSession().invalidate();
		} catch (Exception e) {
			System.err.println("*** Error -- AuthService -- login failure or logout error: " + e.getMessage());
		}
	}
}
