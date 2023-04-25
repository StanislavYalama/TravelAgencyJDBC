package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.PersonalWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;

@Slf4j
@Component
public class ClientRepositoryImpl extends JDBCCustomRepositoryImpl<Client, Integer> implements ClientRepository {

    @Override
    public int findIdByLogin(String login, Connection connection) {
        int userId = 0;
        String query = """
                SELECT id FROM client
                WHERE login = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                userId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    @Override
    public Client findByRequestId(int requestId, Connection connection) {
        String queryClient = """
                SELECT * from client 
                WHERE id IN (SELECT client_id FROM request
                WHERE id = ?)""";
        Client client = new Client();

        try(PreparedStatement preparedStatementClient = connection.prepareStatement(queryClient)) {
            preparedStatementClient.setInt(1, requestId);

            ResultSet resultSet = preparedStatementClient.executeQuery();
            resultSet.next();

            client.setId(resultSet.getInt("id"));
            client.setEmail(resultSet.getString("email"));
            client.setLogin(resultSet.getString("login"));
            client.setPhone(resultSet.getString("phone"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public void changeBalanceByClientId(int clientId, double newBalance, Connection connection) {
        String query = "UPDATE personal_wallet SET balance = ? WHERE client_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonalWallet getPersonalWalletByClientId(int clientId, Connection connection) {
        String query = "SELECT * FROM personal_wallet WHERE client_id = ?";
        PersonalWallet personalWallet = new PersonalWallet();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            personalWallet.setId(resultSet.getInt("id"));
            personalWallet.setClientId(resultSet.getInt("client_id"));
            personalWallet.setBalance(resultSet.getDouble("balance"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personalWallet;
    }
}
