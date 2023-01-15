package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.ClientDocument;
import com.coursework.oneWay.models.PersonalWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ClientRepositoryImpl implements ClientRepository {
    @Override
    public List<Client> findAll(Connection connection) {
        List<Client> clientList = new ArrayList<>();

        String queryClientTable = "SELECT * FROM client";
        String queryPersonalWallet = "SELECT * FROM personal_wallet WHERE client_id = ?";

        try{
            Statement statement = connection.createStatement();
            statement.execute(queryClientTable);
            ResultSet resultSet1 = statement.getResultSet();
            while(resultSet1.next()){
                Client client = new Client();

                client.setId(resultSet1.getInt("id"));
                client.setEmail(resultSet1.getString("email"));
                client.setPhone(resultSet1.getString("phone"));
                client.setLogin(resultSet1.getString("login"));
                client.setPassportId(resultSet1.getInt("passport_id"));

                PreparedStatement preparedStatement2 = connection.prepareStatement(queryPersonalWallet);
                preparedStatement2.setInt(1, client.getId());
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while(resultSet2.next()){
                    PersonalWallet personalWallet = new PersonalWallet();
                    personalWallet.setId(resultSet2.getInt("id"));
                    personalWallet.setClientId(resultSet2.getInt("client_id"));
                    personalWallet.setBalance(resultSet2.getDouble("balance"));

                    client.setPersonalWallet(personalWallet);
                }
//                resultSet2.close();
//                preparedStatement2.close();
            }
//            resultSet1.close();
//            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientList;
    }

    @Override
    public Client findById(int id, Connection connection) {
        Client client = new Client();
        String queryClientTable = "SELECT * FROM client WHERE id = ?";
        String queryPersonalWallet = "SELECT * FROM personal_wallet WHERE client_id = ?";

        try{
            PreparedStatement preparedStatement1 = connection.prepareStatement(queryClientTable);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while(resultSet1.next()){
                client.setId(resultSet1.getInt("id"));
                client.setEmail(resultSet1.getString("email"));
                client.setPhone(resultSet1.getString("phone"));
                client.setLogin(resultSet1.getString("login"));
                client.setPassportId(resultSet1.getInt("passport_id"));

                PreparedStatement preparedStatement2 = connection.prepareStatement(queryPersonalWallet);
                preparedStatement2.setInt(1, id);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while(resultSet2.next()){
                    PersonalWallet personalWallet = new PersonalWallet();
                    personalWallet.setId(resultSet2.getInt("id"));
                    personalWallet.setClientId(resultSet2.getInt("client_id"));
                    personalWallet.setBalance(resultSet2.getDouble("balance"));

                    client.setPersonalWallet(personalWallet);
                }
                resultSet2.close();
                preparedStatement2.close();
            }
//            resultSet1.close();
//            preparedStatement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

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
    public void save(Client client, Connection connection) {
        String query = """
                INSERT INTO client(email, phone, login, passport_id)
                VALUES(?, ?, ?, ?)""";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getEmail());
            preparedStatement.setString(2, client.getPhone());
            preparedStatement.setString(3, client.getLogin());
            preparedStatement.setInt(4, client.getPassportId());
            preparedStatement.executeUpdate();

            log.info("Client {} was saved", client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id, Connection connection) {
        String query = "DELETE FROM client WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            log.info("Client with id = {} was deleted", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassportId(int clientId, int passportId, Connection connection) {
        String query = "UPDATE client SET passport_id = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passportId);
            preparedStatement.setInt(2, clientId);

            log.info("PassportId = {} for client with id = {}", passportId, clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
