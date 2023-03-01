package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourOperator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TourOperatorRepositoryImpl extends JDBCCustomRepositoryImpl<TourOperator, Integer> implements TourOperatorRepository{

    @Override
    public TourOperator findByRequestId(int requestId, Connection connection) {
        TourOperator tourOperator = new TourOperator();
        String query = """
                SELECT * FROM tour_operator
                WHERE id IN (SELECT tour_operator_id FROM tour
                WHERE id IN (SELECT tour_id FROM request
                WHERE id = ?))""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            tourOperator.setId(resultSet.getInt("id"));
            tourOperator.setName(resultSet.getString("name"));
            tourOperator.setEmail(resultSet.getString("email"));
            tourOperator.setPhone(resultSet.getString("phone"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourOperator;
    }
}