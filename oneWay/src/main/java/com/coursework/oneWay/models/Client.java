package com.coursework.oneWay.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Integer id;
    private String phone;
    private String email;
    private String login;
    private Integer passportId;
    private PersonalWallet personalWallet;
}