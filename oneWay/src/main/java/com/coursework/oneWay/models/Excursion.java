package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Excursion {
    private int id;
    private int locationId;
    private String contentType;
    private int countPlaces;
    private String placeName;
}
