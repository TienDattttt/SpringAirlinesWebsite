package com.example.SwipeFlight.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import com.example.SwipeFlight.auth.user.UserService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionUserFilter implements Filter {

    @Autowired
    private UserService userService; // to receive the user's details from database

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession(true);

        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();

            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {

                request.setAttribute("sessionUser", userService.getUserByUserName(authentication.getName()));
            }
        }
        try {

            chain.doFilter(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("*** Error *** " + e.getMessage());

        } catch (ServletException e) {
            throw new IllegalArgumentException("*** Error *** " + e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("*** Error *** " + e.getMessage());
        }
    }
}
