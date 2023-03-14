package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourHotel {

    private Integer id;
    private Integer tourId;
    private Integer hotelId;
}
