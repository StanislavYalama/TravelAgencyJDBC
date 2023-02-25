package com.coursework.oneWay.bean;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
@SessionScope
@Component
public class HttpSessionBean {
    int id = 0;
    String role = "guest";
    String lastUrl = "redirect:/";
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/travel_agency?currentSchema=public",
                    "guest", "guest");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}