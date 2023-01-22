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
    Connection connection;
    int id = 1;
    String role = "client";
    String lastUrl = "redirect:/";

    {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/travel_agency?currentSchema=public",
                    "stasyan", "stasyan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}