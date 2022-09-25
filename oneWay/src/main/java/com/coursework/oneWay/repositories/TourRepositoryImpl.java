package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TourRepositoryImpl extends JDBCCustomRepositoryImpl<Tour, Integer>{

//    private final LoginService loginService;

//    @Override
//    public List<Tour> findAll(Connection connection) throws SQLException {
//        List<Tour> listResult = new ArrayList<>();
//        String query = """
//                        SELECT id, date_start, date_end, price, price_promotion,
//                        description, creator_id, tour_operator_id
//                        FROM tour
//                        """;
//
//        try (Statement stmt = connection.createStatement()) {
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                Tour tour = new Tour();
//                tour.setId(rs.getInt("id"));
//                tour.setDateStart(rs.getDate("date_start"));
//                tour.setDateEnd(rs.getDate("date_end"));
//                tour.setPrice(rs.getDouble("price"));
//                tour.setPriceWithPromotion(rs.getDouble("price_promotion"));
//                tour.setDescription(rs.getString("description"));
//                tour.setTourManagerId(rs.getInt("creator_id"));
//                tour.setTourOperatorId(rs.getInt("tour_operator_id"));
//                listResult.add(tour);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return listResult;
//    }
//
//    @Override
//    public void save(Tour tour, Connection connection) {
////        (id, date_start, date_end, creator_id, description, price, tour_operator_id, price_promotion)
//        String query = """
//                        INSERT INTO tour
//                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
//                        """;
//
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, tour.getId());
//            stmt.setDate(2, new java.sql.Date(tour.getDateStart().getTime()));
//            stmt.setDate(3, new java.sql.Date(tour.getDateEnd().getTime()));
//            stmt.setInt(4, tour.getTourManagerId());
//            stmt.setString(5, tour.getDescription());
//            stmt.setDouble(6,  tour.getPrice());
//            stmt.setInt(7, tour.getTourOperatorId());
//            stmt.setDouble(8, tour.getPriceWithPromotion());
//            int p = stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
