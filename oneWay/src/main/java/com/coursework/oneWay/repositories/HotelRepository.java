package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Hotel;

import java.sql.Connection;
import java.util.List;

public interface HotelRepository{

    public List<Hotel> findByTourId(int tourId, Connection connection);
}
