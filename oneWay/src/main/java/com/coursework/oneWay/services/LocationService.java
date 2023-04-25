package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.repositories.LocationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepositoryImpl locationRepository;

    public List<Location> findByTourId(int tourId, Connection connection){
        return locationRepository.findByTourId(tourId, connection);
    }

    public List<Location> findAll(Connection connection) {
        return locationRepository.findAll(Location.class, connection);
    }

    public Location findById(int id, Connection connection) {
        return locationRepository.findById(Location.class, id, connection);
    }

    public void save(Location location, Connection connection) {
        locationRepository.save(location, connection);
    }

    public List<String> getCountryList(Connection connection) {
        return locationRepository.getCountryList(connection);
    }
}
