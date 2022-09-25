package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourManager;

import java.sql.Connection;

public interface TourManagerRepository {
//    TourManager findByEmail(String email);

    int findIdByLogin(String login, Connection connection);
//
//    @Query(
//            value = "select * from tour_manager_rank()",
//            nativeQuery = true
//    )
//    List<FTourManagerRank> showTourManagerRank();
}