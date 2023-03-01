package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourOperator;

import java.sql.Connection;

public interface TourOperatorRepository extends JDBCCustomRepository<TourOperator, Integer> {

    TourOperator findByRequestId(int requestId, Connection connection);
}
