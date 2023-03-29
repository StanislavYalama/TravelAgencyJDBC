package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Hotel;
import com.coursework.oneWay.models.TourHotel;
import com.coursework.oneWay.repositories.HotelRepositoryImpl;
import com.coursework.oneWay.repositories.TourHotelRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepositoryImpl hotelRepository;
    @Autowired
    private TourHotelRepositoryImpl tourHotelRepository;

    public List<Hotel> findAll(Connection connection){
        return hotelRepository.findAll(Hotel.class, connection);
    }

    public Hotel findById(int id, Connection connection){
        return hotelRepository.findById(Hotel.class, id, connection);
    }

    public List<Hotel> findByTourIdUnspent(int tourId, Connection connection){
        return hotelRepository.findByTourIdUnspent(tourId, connection);
    }

    public void delete(int id, Connection connection){
        hotelRepository.deleteById(Hotel.class, id, connection);
    }

    public void save(Hotel hotel, Connection connection){
        hotelRepository.save(hotel, connection);
    }

    public List<Hotel> findByTourId(Integer tourId, Connection connection) {
        return hotelRepository.findByTourId(tourId, connection);
    }

//    public List<Hotel> findByTourIdUnspent(Integer request_id, Connection connection) {
//        return hotelRepository.findByTourIdUnspent(request_id, connection);
//    }

    public void saveTourHotel(TourHotel tourHotel, Connection connection) {
        tourHotelRepository.save(tourHotel, connection);
    }

    public void deleteFromTourHotel(TourHotel tourHotel, Connection connection) {
        tourHotelRepository.delete(tourHotel, connection);
    }
}
