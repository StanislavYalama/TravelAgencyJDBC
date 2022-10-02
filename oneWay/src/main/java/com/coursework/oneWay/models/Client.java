package com.coursework.oneWay.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class Client {

    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String login;
    private PersonalWallet personalWallet;
    private List<ClientDocument> clientDocument;
}