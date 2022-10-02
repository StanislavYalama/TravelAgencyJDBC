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
        String queryClientDocuments = "SELECT * FROM client_document WHERE client_id = ?";

        try{
            Statement statement = connection.createStatement();
            statement.execute(queryClientTable);
            ResultSet resultSet1 = statement.getResultSet();
            while(resultSet1.next()){
                Client client = new Client();

                client.setId(resultSet1.getInt("id"));
                client.setName(resultSet1.getString("name"));
                client.setEmail(resultSet1.getString("email"));
                client.setPhone(resultSet1.getString("phone"));
                client.setLogin(resultSet1.getString("login"));

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

                PreparedStatement preparedStatement3 = connection.prepareStatement(queryClientDocuments);
                preparedStatement3.setInt(1, client.getId());
                ResultSet resultSet3 = preparedStatement3.executeQuery();
                List<ClientDocument> clientDocumentList = new ArrayList<>();
                while(resultSet3.next()){
                    ClientDocument clientDocument = new ClientDocument();
                    clientDocument.setId(resultSet3.getInt("id"));
                    clientDocument.setClientId(resultSet3.getInt("client_id"));
                    clientDocument.setDocumentId(resultSet3.getInt("document_id"));
                    clientDocument.setPhotoPath(resultSet3.getString("photo_path"));
                    clientDocumentList.add(clientDocument);
                }
//                resultSet3.close();
//                preparedStatement3.close();

                client.setClientDocument(clientDocumentList);
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
        String queryClientDocuments = "SELECT * FROM client_document WHERE client_id = ?";

        try{
            PreparedStatement preparedStatement1 = connection.prepareStatement(queryClientTable);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while(resultSet1.next()){
                client.setId(resultSet1.getInt("id"));
                client.setName(resultSet1.getString("name"));
                client.setEmail(resultSet1.getString("email"));
                client.setPhone(resultSet1.getString("phone"));
                client.setLogin(resultSet1.getString("login"));

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

                PreparedStatement preparedStatement3 = connection.prepareStatement(queryClientDocuments);
                preparedStatement3.setInt(1, id);
                ResultSet resultSet3 = preparedStatement3.executeQuery();
                List<ClientDocument> clientDocumentList = new ArrayList<>();
                while(resultSet3.next()){
                    ClientDocument clientDocument = new ClientDocument();
                    clientDocument.setId(resultSet3.getInt("id"));
                    clientDocument.setClientId(resultSet3.getInt("client_id"));
                    clientDocument.setDocumentId(resultSet3.getInt("document_id"));
                    clientDocument.setPhotoPath(resultSet3.getString("photo_path"));
                    clientDocumentList.add(clientDocument);
                }
                resultSet3.close();
                preparedStatement3.close();

                client.setClientDocument(clientDocumentList);
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
                INSERT INTO client(id, name, email, phone, login)
                VALUES(?, ?, ?, ?, ?)""";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getLogin());
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
}
