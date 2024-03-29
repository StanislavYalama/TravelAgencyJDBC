package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.models.TourView;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TourRepository {

    List<TourView> findAllTourView(Connection connection);

    Tour findByRequestId(int requestId, Connection connection);

    void save(Tour tour, Connection connection);

    void saveLocations(List<Location> locationList, int tourId, Connection connection);

    void deleteLocation(int tourId, int locationId, Connection connection);

    void saveExcursion(int tourId, int excursionId, Connection connection);

    TourView findByIdTourViews(int tourId, Connection connection);

    void deleteExcursion(int tourId, int excursionId, Connection connection);

    void closeTourByCountryName(String country, Connection connection);
}
