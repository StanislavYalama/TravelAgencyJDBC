package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.models.TourView;
import com.coursework.oneWay.repositories.TourRepository;
import com.coursework.oneWay.repositories.TourRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourService {

    private final TourRepositoryImpl tourRepository;

    public List<Tour> findAll(Connection connection) throws SQLException {
        return tourRepository.findAll(Tour.class, connection);
    }
    public Tour findById(int tourId, Connection connection){
        return tourRepository.findById(Tour.class, tourId, connection);
    }
    public List<TourView> findAllTourViews(Connection connection){
        return tourRepository.findAllTourView(connection);
    }
    public void makeVisible(int tourId, Connection connection){
        tourRepository.update(Tour.class, tourId, "is_visible", true, connection);
    }
    public Tour findByRequestId(int requestId, Connection connection){
        return tourRepository.findByRequestId(requestId, connection);
    }
    public void save(Tour tour, Connection connection){
        tourRepository.save(tour, connection);
    }
    public void deleteById(int tourId, Connection connection){
        tourRepository.deleteById(Tour.class, tourId, connection);
    }
    public void saveLocations(List<Location> locationList, int tourId, Connection connection){
        tourRepository.saveLocations(locationList, tourId, connection);
    }
    public void deleteLocation(int tourId, int locationId, Connection connection){
        tourRepository.deleteLocation(tourId, locationId, connection);
    }
    public void saveExcursion(int tourId, int excursionId, Connection connection){
        tourRepository.saveExcursion(tourId, excursionId, connection);
    }

    public TourView findByIdTourViews(int tourId, Connection connection) {
        return tourRepository.findByIdTourViews(tourId, connection);
    }
}
