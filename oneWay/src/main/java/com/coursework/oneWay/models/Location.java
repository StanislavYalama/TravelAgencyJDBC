package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Integer id;
    private String country;
    private String city;
    private String description;
    private BigDecimal price;
    private Integer creatorId;
}
