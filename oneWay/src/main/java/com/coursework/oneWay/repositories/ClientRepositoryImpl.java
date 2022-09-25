package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;
import org.springframework.stereotype.Component;

import java.sql.*;

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
}
