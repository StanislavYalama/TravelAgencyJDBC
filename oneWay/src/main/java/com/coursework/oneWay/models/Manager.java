package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Manager extends Worker{
    private Integer id;
    private String name;
    private String phone;
    private String email;
}
