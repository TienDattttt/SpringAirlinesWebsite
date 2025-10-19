package com.example.SwipeFlight.server;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import com.example.SwipeFlight.auth.user.User;
import com.example.SwipeFlight.auth.user.UserService;

@Component
public class DatabaseInitializer {
    private DataSource dataSource;
    @Autowired
    private UserService userService;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initializeDatabase() {

        try (Connection connection = dataSource.getConnection()) {
            try {

                if (userService.getUserByUserName("admin") == null) {

                    User admin = new User("admin", "swipeflight@gmail.com", "12345", true);
                    userService.insertUser(admin);
                } else
                    System.out.println("==> \"data.sql\" script has already run.");
            } catch (ScriptStatementFailedException e) {
                System.err.println("*** Error -- Script -- script execution failed: " + e.getMessage());
            } catch (ScriptException e) {
                System.err.println("*** Error -- Script -- script error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("*** Error -- SQL -- database access error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("*** Error -- DataSource -- datasource is null.");
        } catch (Exception e) {
            System.err.println("*** Error -- DataSource -- datasource error: " + e.getMessage());
        }

    }
}
