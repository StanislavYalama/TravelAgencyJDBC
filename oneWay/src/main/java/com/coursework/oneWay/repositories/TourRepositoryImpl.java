package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.models.TourView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TourRepositoryImpl extends JDBCCustomRepositoryImpl<Tour, Integer> implements TourRepository {

    @Override
    public List<TourView> findAllTourView(Connection connection) {
        List<TourView> tourViewList = new ArrayList<>();
        String query = "SELECT * FROM tour_view";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                TourView tourView = new TourView(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date_start").toLocalDate(),
                        resultSet.getDate("date_end").toLocalDate(),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getInt("worker_id"),
                        resultSet.getInt("tour_operator_id"),
                        resultSet.getBoolean("visible"),
                        resultSet.getDouble("price_promotion"),
                        resultSet.getInt("discount_percentage"),
                        resultSet.getString("type"),
                        resultSet.getInt("number_of_seats"),
                        resultSet.getString("photo_path")
                );

                tourViewList.add(tourView);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourViewList;
    }

    @Override
    public Tour findByRequestId(int requestId, Connection connection) {
        Tour tour = new Tour();
        String query = "SELECT * FROM tour WHERE id = (SELECT tour_id FROM request WHERE id = ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tour.setId(resultSet.getInt("id"));
            tour.setDateStart(resultSet.getDate("date_start").toLocalDate());
            tour.setDateEnd(resultSet.getDate("date_end").toLocalDate());
            tour.setDescription(resultSet.getString("description"));
            tour.setWorkerId(resultSet.getInt("worker_id"));
            tour.setPrice(resultSet.getDouble("price"));
            tour.setTourOperatorId(resultSet.getInt("tour_operator_id"));
            tour.setType(resultSet.getString("type"));
            tour.setNumberOfSeats(resultSet.getInt("number_of_seats"));
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            preparedStatement.setInt(2, excursionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TourView findByIdTourViews(int tourId, Connection connection) {
        TourView tourView = new TourView();
        String query = "SELECT * FROM tour_view WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tourView.setId(resultSet.getInt("id"));
            tourView.setDateStart(resultSet.getDate("date_start").toLocalDate());
            tourView.setDateEnd(resultSet.getDate("date_end").toLocalDate());
            tourView.setPrice(resultSet.getDouble("price"));
            tourView.setDescription(resultSet.getString("description"));
            tourView.setWorkerId(resultSet.getInt("worker_id"));
            tourView.setTourOperatorId(resultSet.getInt("tour_operator_id"));
            tourView.setVisible(resultSet.getBoolean("visible"));
            tourView.setPricePromotion(resultSet.getDouble("price_promotion"));
            tourView.setDiscountPercentage(resultSet.getInt("discount_percentage"));
            tourView.setType(resultSet.getString("type"));
            tourView.setNumberOfSeats(resultSet.getInt("number_of_seats"));
            tourView.setPhotoPath(resultSet.getString("photo_path"));
            tourView.setName(resultSet.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourView;
    }

    @Override
    public void deleteExcursion(int tourId, int excursionId, Connection connection) {
        String query = "DELETE FROM tour_excursion WHERE tour_id = ? AND excursion_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            preparedStatement.setInt(2, excursionId);
            preparedStatement.executeUpdate();
            log.info("Delete row from tour_location table with tourId = {} and locationId = {}",
                    tourId, excursionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeTourByCountryName(String country, Connection connection) {
        String query = "CALL cancel_tour_by_country(?)";

        try(CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setString(1, country);
            callableStatement.execute();
            log.info("Was executed procedure cancel_tour_by_country({}).", country);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}