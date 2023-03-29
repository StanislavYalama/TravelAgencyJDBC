package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourHotel;

import java.sql.Connection;

public interface TourHotelRepository {

    void delete(TourHotel tourHotel, Connection connection);
}
