package com.coursework.oneWay.services;


import com.coursework.oneWay.models.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class LoginService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PassportService passportService;
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

    //TODO
    public void createUser(Client client, String clientPassword, String clientName, Connection connection) throws SQLException {
        String queryCreateUser = "CREATE USER ".concat(client.getLogin()).concat(" WITH PASSWORD '")
                .concat(clientPassword).concat("' IN GROUP client");
        String queryInsertPassport = "INSERT INTO passport(name) VALUES(?)";
        int clientId = 0;
        int passportId = 0;

        try {
            int previousLevel = connection.getTransactionIsolation();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.executeUpdate(queryCreateUser);
            statement.close();

            PreparedStatement preparedStatement = connection.prepareStatement(queryInsertPassport);
            preparedStatement.setString(1, clientName);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            clientId = clientService.findIdByLogin(client.getLogin(), connection);
            passportId = passportService.getCurrentPassportIdSequenceValue(connection);
            if(clientId !=  0 && passportId != 0){
                clientService.updatePassportId(clientId, passportId, connection);
            }

            connection.commit();
            connection.setTransactionIsolation(previousLevel);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clientService.save(client, connection);
    }
}
