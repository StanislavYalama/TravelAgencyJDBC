//package com.coursework.oneWay.models;
//
//import com.tourAgency.oneWay.enums.ROLES;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.Transient;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Data
//@NoArgsConstructor
//public class User{
//    private String email;
//    private String password;
//    private ROLES role;
//    private Connection connection;
//
//    public void closeConnection() throws SQLException {
//        connection.close();
//        connection = null;
//    }
//}
