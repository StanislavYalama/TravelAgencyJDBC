package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TourRepository {

//    List<Tour> findAll(Connection connection) throws SQLException;

    void save(Tour tour, Connection connection);

    void saveLocations(List<Location> locationList, int tourId, Connection connection);



//    List<Tour> findByDateStart(Date dateStart);
//
//    @Query(
//            value = "select * from tour t where t.id IN (select tour_id from request r where client_id = ?1) ",
//            nativeQuery = true
//    )
//    List<Tour> findVisitedToursByClientId(int id);
//
//    @Query(
//            value = "select * from tour_rank()",
//            nativeQuery = true
//    )
//    Map<Tour, Integer> showRankByPopular();
//
//    @Query(
//            value = "select * from tour_profit()",
//            nativeQuery = true
//    )
//    Map<Tour, Double> showRankByProfit();
}
