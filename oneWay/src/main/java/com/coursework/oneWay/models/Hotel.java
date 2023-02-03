package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    private int id;
    private String name;
    private String address;
    private String country;
    private String city;
    private String typeFood;
    private int quality;
    private int placesCount;
    private int locationId;
}
