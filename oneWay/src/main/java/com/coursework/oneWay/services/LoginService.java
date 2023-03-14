package com.coursework.oneWay.services;


import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class LoginService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private WorkerService workerService;

    public Connection getConnection(String name, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/travel_agency", name, password);
    }

    public int getUserId(String login, Connection connection){
        int userId = 0;
        userId = clientService.findIdByLogin(login, connection);
        if(userId == 0){
            userId = workerService.findIdByLogin(login, connection);
        }

        return userId;
    }

    public String getRole(String name, Connection connection) throws SQLException {
        String role = "";
        String query = """
                SELECT rolname FROM pg_roles
                WHERE pg_has_role(?, oid, 'member') AND rolname != ?""";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            role = resultSet.getString(1);
        }

        return role;
    }

    public void registerClient(Client client, String password, Connection connection){
        createDBUser(client.getLogin(), password, "client", connection);
        clientService.save(client, connection);
    }

    public void registerWorker(Worker worker, String password, Connection connection){
        createDBUser(worker.getLogin(), password, worker.getRole(), connection);
        workerService.save(worker, connection);
    }

    private void createDBUser(String login, String password, String role, Connection connection){
        String query = "CREATE USER ".concat(login).concat(" WITH ENCRYPTED PASSWORD '")
                .concat(password).concat("' IN GROUP ").concat(role);

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserPassword(String login, String password, Connection connection){
        String query = "ALTER USER ".concat(login).concat(" WITH ENCRYPTED PASSWORD '".concat(password).concat("'"));

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fireTheWorker(String login, Connection connection){
        String query = "ALTER USER ".concat(login).concat(" WITH ENCRYPTED PASSWORD '".concat("uvolen").concat("'"));

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDBRole(String role, Connection connection) {
        String query = "SET ROLE ".concat(role);

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
