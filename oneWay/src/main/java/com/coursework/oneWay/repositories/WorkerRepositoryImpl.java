package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Worker;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class WorkerRepositoryImpl extends JDBCCustomRepositoryImpl<Worker, Integer> implements WorkerRepository {

    @Override
    public void saveNewWorker(Worker worker, String password, Connection connection){
        String query = "CREATE USER " + worker.getLogin() + " WITH ENCRYPTED PASSWORD '" + password +
                "' IN GROUP " + worker.getRole().name().toLowerCase();

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save(worker, connection);
    }

    @Override
    public int findIdByLogin(String login, Connection connection) {
        int id = 0;
        String query = "SELECT id FROM worker WHERE login = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            id = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
