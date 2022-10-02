package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.services.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TourRepositoryImpl extends JDBCCustomRepositoryImpl<Tour, Integer> implements TourRepository {
    @Override
    public void saveLocations(List<Location> locationList, int tourId, Connection connection) {
        String query = "INSERT INTO tour_location(tour_id, location_id, location_order) VALUES";
        for (int i = 0; i < locationList.size(); i++) {
            if (i == locationList.size() - 1) {
                query = query.concat("(?, ?, ?)");
            } else {
                query = query.concat("(?, ?, ?), ");
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int locationOrderKol = 1;
            int temp = 1;
            for (int i = 0; i < locationList.size(); i++) {
                preparedStatement.setInt(i + temp, tourId);
                temp++;
                preparedStatement.setInt(i + temp, locationList.get(i).getId());
                temp++;
                preparedStatement.setInt(i + temp, locationOrderKol);
//                temp++;
                locationOrderKol++;
            }

            preparedStatement.executeUpdate();
            log.info("Locations {} was linked with tour {}", locationList, tourId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}