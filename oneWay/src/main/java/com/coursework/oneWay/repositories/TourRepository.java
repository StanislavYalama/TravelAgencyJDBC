package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TourRepository {

    void save(Tour tour, Connection connection);

    void saveLocations(List<Location> locationList, int tourId, Connection connection);

    void deleteLocation(int tourId, int locationId, Connection connection);
}
