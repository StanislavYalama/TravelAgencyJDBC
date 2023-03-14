package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    private Integer id;
    private String name;
    private String address;
    private String country;
    private String city;
    private String typeFood;
    private Integer quality;
    private Integer placesCount;
    private Integer locationId;
}
