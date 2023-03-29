package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcursionView {
    private Integer id;
    private String city;
    private String placeName;
    private String contentType;
    private Integer locationId;
}