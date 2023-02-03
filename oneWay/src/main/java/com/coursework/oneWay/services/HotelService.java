package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Hotel;
import com.coursework.oneWay.repositories.HotelRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepositoryImpl hotelRepository;

    public List<Hotel> findAll(Connection connection){
        return hotelRepository.findAll(Hotel.class, connection);
    }

    public Hotel findById(int id, Connection connection){
        return hotelRepository.findById(Hotel.class, id, connection);
    }

    public List<Hotel> findByTourId(int tourId, Connection connection){
        return hotelRepository.findByTourId(tourId, connection);
    }

    public void delete(int id, Connection connection){
        hotelRepository.deleteById(Hotel.class, id, connection);
    }

    public void save(Hotel hotel, Connection connection){
        hotelRepository.save(hotel, connection);
    }

    public List<Hotel> findByTourId(Integer request_id, Connection connection) {
        return hotelRepository.findByTourId(request_id, connection);
    }
}
