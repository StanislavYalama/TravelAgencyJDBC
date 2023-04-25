package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.models.TourImg;
import com.coursework.oneWay.models.TourView;
import com.coursework.oneWay.repositories.TourImgRepositoryImpl;
import com.coursework.oneWay.repositories.TourRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class TourService {

    @Autowired
    private TourRepositoryImpl tourRepository;
    @Autowired
    private TourImgRepositoryImpl tourImgRepository;

    public List<Tour> findAll(Connection connection) throws SQLException {
        return tourRepository.findAll(Tour.class, connection);
    }
    public Tour findById(int tourId, Connection connection){
        return tourRepository.findById(Tour.class, tourId, connection);
    }
    public List<TourView> findAllTourViews(Connection connection){
        return tourRepository.findAllTourView(connection);
    }
    public void changeVisible(int tourId, Connection connection){
        boolean newVisibleValue;
        Tour tour = tourRepository.findById(Tour.class, tourId, connection);

        if(tour.isVisible()){
            newVisibleValue = false;
        } else{
            newVisibleValue = true;
        }

        tourRepository.update(Tour.class, tourId, "visible", newVisibleValue, connection);
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

    public void saveTourImg(TourImg tourImg, Connection connection){
        tourImgRepository.save(tourImg, connection);
    }

    public void deleteExcursion(int tourId, int excursionId, Connection connection) {
        tourRepository.deleteExcursion(tourId, excursionId, connection);
    }

    public void closeTourByCountryName(String country, Connection connection) {
        tourRepository.closeTourByCountryName(country, connection);
    }
}
