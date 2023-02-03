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
public class HotelRepositoryImpl extends JDBCCustomRepositoryImpl<Hotel, Integer>{

    public List<Hotel> findByTourId(int requestId, Connection connection) {
        List<Hotel> hotelList = new ArrayList<>();
        String query = """
                SELECT h.id, h.name, h.address, h.country, h.city, h.type_food, h.quality, h.location_id, h.places_count FROM hotel h
                RIGHT JOIN request_hotel rh ON h.id=rh.hotel_id
                WHERE request_id = ?""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
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
