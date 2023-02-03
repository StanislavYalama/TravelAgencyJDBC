package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.RequestPassport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class RequestPassportRepositoryImpl extends JDBCCustomRepositoryImpl<RequestPassport, Integer>{
    public int getIdByPassportAndRequest(int passportId, int requestId, Connection connection) {
        int id = 0;
        String query = """
                SELECT id FROM request_passport
                WHERE passport_id = ? AND request_id = ?""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passportId);
            preparedStatement.setInt(2, requestId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
