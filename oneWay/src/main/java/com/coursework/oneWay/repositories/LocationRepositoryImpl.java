package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import org.springframework.stereotype.Component;

import java.sql.*;
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
                location.setWorkerId(resultSet.getInt("worker_id"));
                locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locationList;
    }

    @Override
    public List<String> getCountryList(Connection connection) {
        String query = "SELECT * FROM country_view";
        List<String> countryList = new ArrayList<>();

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                countryList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryList;
    }
}
