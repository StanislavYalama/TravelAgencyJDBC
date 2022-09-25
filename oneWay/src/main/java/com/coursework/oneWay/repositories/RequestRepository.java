package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Request;

import java.sql.Connection;
import java.util.List;

public interface RequestRepository extends JDBCCustomRepository<Request, Integer> {

    List<Request> findUnadmitted(Connection connection);

    List<Request> findAdmitted(Connection connection);

    List<Request> findByClientId(int id, Connection connection);
//    @Transactional
//    @Modifying
//    @Query("input into Request(client_id, date, tour_id) values(?1, NOW(), ?2)")
//    void saveNew(int clientId, int tourId);
}