package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Excursion {
    private Integer id;
    private Integer locationId;
    private String contentType;
    private Integer countPlaces;
    private String placeName;
}
