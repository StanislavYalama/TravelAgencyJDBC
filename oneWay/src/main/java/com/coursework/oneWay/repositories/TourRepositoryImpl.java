package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
@Slf4j
public class TourRepositoryImpl extends JDBCCustomRepositoryImpl<Tour, Integer> implements TourRepository {

    @Override
    public Tour findByRequestId(int requestId, Connection connection) {
        Tour tour = new Tour();
        String query = "SELECT * FROM tour WHERE id = (SELECT tour_id FROM request WHERE id = ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tour.setId(resultSet.getInt("id"));
            tour.setDateStart(resultSet.getDate("date_start").toLocalDate());
            tour.setDateEnd(resultSet.getDate("date_end").toLocalDate());
            tour.setDescription(resultSet.getString("description"));
            tour.setWorkerId(resultSet.getInt("worker_id"));
            tour.setLocationCount(resultSet.getInt("location_count"));
            tour.setPrice(resultSet.getDouble("price"));
            tour.setPricePromotion(resultSet.getDouble("price_promotion"));
            tour.setTourOperatorId(resultSet.getInt("tour_operator_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tour;
    }

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

    @Override
    public void deleteLocation(int tourId, int locationId, Connection connection) {
        String query = "DELETE FROM tour_location WHERE tour_id = ? AND location_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            preparedStatement.setInt(2, locationId);
            preparedStatement.executeUpdate();
            log.info("Delete row from tour_location table with tourId = {} and locationId = {}",
                    tourId, locationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveExcursion(int tourId, int excursionId, Connection connection) {
        String query = """
                INSERT INTO tour_excursion(tour_id, excursion_id)
                VALUES(?, ?)""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, tourId);
            preparedStatement.setInt(2, excursionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}