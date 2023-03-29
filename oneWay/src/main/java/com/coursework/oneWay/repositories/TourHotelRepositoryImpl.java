package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourHotel;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class TourHotelRepositoryImpl extends JDBCCustomRepositoryImpl<TourHotel, Integer> implements TourHotelRepository{

    public void delete(TourHotel tourHotel, Connection connection) {
        String query = "DELETE FROM tour_hotel WHERE tour_id = ? AND hotel_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourHotel.getTourId());
            preparedStatement.setInt(2, tourHotel.getHotelId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
