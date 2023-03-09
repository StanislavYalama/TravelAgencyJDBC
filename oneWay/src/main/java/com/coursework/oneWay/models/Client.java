package com.coursework.oneWay.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String login;
}