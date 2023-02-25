package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Excursion;

import java.sql.Connection;
import java.util.List;

public interface ExcursionRepository {

    List<Excursion> findByTourId(int tourId, Connection connection);
}
