package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Excursion;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcursionRepositoryImpl extends JDBCCustomRepositoryImpl<Excursion, Integer> implements ExcursionRepository {

    public List<Excursion> findByTourId(int tourId, Connection connection){
        List<Excursion> excursionList = new ArrayList<>();
        String query = """
                SELECT * FROM excursion
                WHERE location_id IN (SELECT location_id FROM tour_location
                 WHERE tour_id = ?)
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Excursion excursion = new Excursion();
                excursion.setId(resultSet.getInt("id"));
                excursion.setContentType(resultSet.getString("content_type"));
                excursion.setCountPlaces(resultSet.getInt("count_places"));
                excursion.setPlaceName(resultSet.getString("place_name"));
                excursion.setLocationId(resultSet.getInt("location_id"));

                excursionList.add(excursion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return excursionList;
    }
}
