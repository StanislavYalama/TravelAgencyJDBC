package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;

import java.sql.Connection;
import java.util.List;

public interface LocationRepository {

    List<Location> findByTourId(int id, Connection connection);
}
