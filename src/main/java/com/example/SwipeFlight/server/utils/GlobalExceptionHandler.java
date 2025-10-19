package com.example.SwipeFlight.server.utils;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.SwipeFlight.server.utils.custom_exceptions.RequestAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionAttributeNotFoundException;
import com.example.SwipeFlight.server.utils.custom_exceptions.SessionIsInvalidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadSqlGrammarException.class)
    public String handleBadSqlGrammarException(BadSqlGrammarException e, Model model) {
        model.addAttribute("error_message", e.getClass() + ": " + e.getMessage());
        return "error"; // display page
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
            Model model) {
        model.addAttribute("error_message", ex.getClass() + ": "
                + "Required request parameter '" + ex.getParameterName() + "' is missing.");
        return "error"; // display page
    }

    @ExceptionHandler(RequestAttributeNotFoundException.class)
    public String handleRequestAttributeNotFoundException(RequestAttributeNotFoundException e, Model model) {
        model.addAttribute("error_message", e.getClass() + ": " + e.getMessage());
        return "error"; // display page
    }

    @ExceptionHandler(SessionAttributeNotFoundException.class)
    public String handleSessionAttributeNotFoundException(SessionAttributeNotFoundException e, Model model) {
        model.addAttribute("error_message", e.getClass() + ": " + e.getMessage());
        return "error"; // display page
    }

    @ExceptionHandler(SessionIsInvalidException.class)
    public String handleSessionIsInvalidException(SessionIsInvalidException e, Model model) {
        model.addAttribute("error_message", e.getClass() + ": " + e.getMessage());
        return "error"; // display page
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error_message", e.getClass() + ": " + e.getMessage());
        return "error"; // display page
    }
}
