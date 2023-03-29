package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Excursion;
import com.coursework.oneWay.models.ExcursionView;
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
                excursion.setPlaceName(resultSet.getString("place_name"));
                excursion.setLocationId(resultSet.getInt("location_id"));

                excursionList.add(excursion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return excursionList;
    }

    @Override
    public List<Excursion> findByTourIdUnspent(int id, Connection connection) {
        List<Excursion> excursionList = new ArrayList<>();
        String query = """
                SELECT * FROM excursion
                WHERE location_id IN (SELECT location_id FROM tour_location
                    WHERE tour_id = ?) AND id NOT IN(SELECT excursion_id FROM tour_excursion
                    WHERE tour_id = ?)""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Excursion excursion = new Excursion();
                excursion.setId(resultSet.getInt("id"));
                excursion.setContentType(resultSet.getString("content_type"));
                excursion.setPlaceName(resultSet.getString("place_name"));
                excursion.setLocationId(resultSet.getInt("location_id"));

                excursionList.add(excursion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return excursionList;
    }

    @Override
    public List<ExcursionView> findByTourIdExcursionView(int tourId, Connection connection) {
        List<ExcursionView> excursionList = new ArrayList<>();
        String query = """
                SELECT * FROM excursion_view
                WHERE id IN (SELECT excursion_id FROM tour_excursion
                    WHERE tour_id = ?)""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ExcursionView excursion = new ExcursionView();
                excursion.setId(resultSet.getInt("id"));
                excursion.setContentType(resultSet.getString("content_type"));
                excursion.setPlaceName(resultSet.getString("place_name"));
                excursion.setLocationId(resultSet.getInt("location_id"));
                excursion.setCity(resultSet.getString("city"));

                excursionList.add(excursion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return excursionList;
    }
}
