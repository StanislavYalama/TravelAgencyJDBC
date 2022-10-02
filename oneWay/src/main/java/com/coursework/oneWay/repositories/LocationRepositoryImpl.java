package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocationRepositoryImpl extends JDBCCustomRepositoryImpl<Location, Integer> implements LocationRepository{
    @Override
    public List<Location> findByTourId(int tourId, Connection connection) {
        String query = """
                SELECT * FROM location l
                WHERE id IN (SELECT location_id FROM tour_location WHERE tour_id = ?)
                """;
        List<Location> locationList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Location location = new Location();
                location.setId(resultSet.getInt("id"));
                location.setCountry(resultSet.getString("country"));
                location.setCity(resultSet.getString("city"));
                location.setDescription(resultSet.getString("description"));
                location.setPrice(resultSet.getBigDecimal("price"));
                location.setCreatorId(resultSet.getInt("creator_id"));
                locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locationList;
    }
}
