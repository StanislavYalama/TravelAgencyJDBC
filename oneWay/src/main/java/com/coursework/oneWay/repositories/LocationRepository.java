package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Location;

import java.sql.Connection;
import java.util.List;

public interface LocationRepository {

//    @Query(
//            value = "select * from Location l inner join tour_location tl on l.id=tl.location_id where tl.tour_id=?1 ",
//            nativeQuery = true
//    )
    List<Location> findByTourId(int id, Connection connection);
}
