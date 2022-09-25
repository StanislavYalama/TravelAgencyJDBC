package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Manager;

import java.sql.Connection;

public interface ManagerRepository {
//    Manager findByEmail(String email);

    int findIdByLogin(String login, Connection connection);
//    @Query(
//            value = "select * from manager_rank()",
//            nativeQuery = true
//    )
//    List<FManagerRank> showRank();
}
