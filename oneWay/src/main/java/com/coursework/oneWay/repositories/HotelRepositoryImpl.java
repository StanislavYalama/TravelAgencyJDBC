package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Hotel;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HotelRepositoryImpl extends JDBCCustomRepositoryImpl<Hotel, Integer> implements HotelRepository{

    @Override
    public List<Hotel> findByTourId(int tourId, Connection connection) {
        List<Hotel> hotelList = new ArrayList<>();
        String query = """
                SELECT * FROM hotel
                WHERE location_id IN (SELECT location_id FROM tour_location
                	WHERE tour_id = ?)""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Hotel hotel = new Hotel();
                hotel.setId(resultSet.getInt("id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setAddress(resultSet.getString("address"));
                hotel.setCountry(resultSet.getString("country"));
                hotel.setCity(resultSet.getString("city"));
                hotel.setTypeFood(resultSet.getString("type_food"));
                hotel.setQuality(resultSet.getInt("quality"));
                hotel.setLocationId(resultSet.getInt("location_id"));
                hotel.setPlacesCount(resultSet.getInt("places_count"));
                hotelList.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelList;
    }

    @Override
    public List<Hotel> findByTourIdUnspent(int tourId, Connection connection) {
        List<Hotel> hotelList = new ArrayList<>();
        String query = """
                SELECT * FROM hotel
                WHERE location_id IN (SELECT location_id FROM tour_location
                	WHERE tour_id = ?) AND id NOT IN (SELECT hotel_id FROM tour_hotel
                	WHERE tour_id = ?)""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            preparedStatement.setInt(2, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Hotel hotel = new Hotel();
                hotel.setId(resultSet.getInt("id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setAddress(resultSet.getString("address"));
                hotel.setCountry(resultSet.getString("country"));
                hotel.setCity(resultSet.getString("city"));
                hotel.setTypeFood(resultSet.getString("type_food"));
                hotel.setQuality(resultSet.getInt("quality"));
                hotel.setLocationId(resultSet.getInt("location_id"));
                hotel.setPlacesCount(resultSet.getInt("places_count"));
                hotelList.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelList;
    }
}
