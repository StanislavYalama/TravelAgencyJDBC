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
    int id = 5;
    String role = "client";

    {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/travel_agency?currentSchema=public",
                    "popan", "parol");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}