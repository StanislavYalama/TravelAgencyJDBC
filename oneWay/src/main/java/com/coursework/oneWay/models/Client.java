package com.coursework.oneWay.models;

import lombok.*;

@Data
@NoArgsConstructor
public class Client {

    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String certificateResidence;
    private String certificateCovid;
    private String passport;
    private String login;
}