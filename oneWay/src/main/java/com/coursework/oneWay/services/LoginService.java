package com.coursework.oneWay.services;


import com.coursework.oneWay.models.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final ClientService clientService;
    private final ManagerService managerService;
    private final TourManagerService tourManagerService;

    public Connection getConnection(String name, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/travel_agency", name, password);
    }

    public int getUserId(String name, Connection connection){
        int userId = 0;
        userId = clientService.findIdByLogin(name, connection);
        if(userId == 0){
            userId = managerService.findIdByLogin(name, connection);
            if(userId == 0){
                userId = tourManagerService.findIdByLogin(name, connection);
                if(userId == 0){
                    System.out.println("Login not found");
                }
            }
        }

        return userId;
    }

    public String getRole(String name, Connection connection) throws SQLException {
        String role = "";
        String query = """
                SELECT rolname FROM pg_roles 
                WHERE pg_has_role( ?, oid, 'member') AND rolname != ?""";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            role = resultSet.getString(1);
        }

        return role;
    }

    // TO DO (LoginController && registration.html)
    public void createUser(Client client, Connection connection) throws SQLException {
        String queryCreateUser = "CREATE USER ".concat(client.getLogin()).concat(" WITH PASSWORD '")
                .concat(client.getPassword()).concat("' IN GROUP client");

        Connection connectionPostgres = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/travel_agency?currentSchema=public",
                    "postgres", "1201");

        try (Statement statement = connectionPostgres.createStatement()){
//            preparedStatement.setString(1, client.getLogin());
//            preparedStatement.setString(1, client.getPassword());
            statement.executeUpdate(queryCreateUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPostgres.close();

        clientService.save(client, connection);
    }
}
