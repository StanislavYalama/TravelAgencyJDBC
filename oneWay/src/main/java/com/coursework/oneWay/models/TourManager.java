package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourManager{
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String password;
}
