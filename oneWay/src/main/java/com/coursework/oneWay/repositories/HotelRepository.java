package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Hotel;

import java.sql.Connection;
import java.util.List;

public interface HotelRepository{

    List<Hotel> findByTourId(int tourId, Connection connection);

    List<Hotel> findByTourIdUnspent(int tourId, Connection connection);

}
