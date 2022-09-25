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
}
