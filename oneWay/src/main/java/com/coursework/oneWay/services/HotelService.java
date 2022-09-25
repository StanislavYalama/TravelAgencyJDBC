//package com.coursework.oneWay.services;
//
//import com.tourAgency.oneWay.models.Hotel;
//import com.tourAgency.oneWay.models.Location;
//import com.tourAgency.oneWay.repositories.HotelRepository;
//import com.tourAgency.oneWay.repositories.LocationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class HotelService {
//
//    private final HotelRepository hotelRepository;
//    private final LocationRepository locationRepository;
//
//    public List<Hotel> findAll(){
//        return hotelRepository.findAll();
//    }
//
//    public void delete(Integer id){
//        hotelRepository.deleteById(id);
//    }
//
//    public void save(Hotel hotel){
//        hotelRepository.save(hotel);
//    }
//
//    public Hotel getById(Integer id){
//        return hotelRepository.getById(id);
//    }
//
//    public List<Location> findByTourId(Integer id) {
//        return locationRepository.findByTourId(id.intValue());
//    }
//}
