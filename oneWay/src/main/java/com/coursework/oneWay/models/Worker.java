package com.coursework.oneWay.models;

import com.coursework.oneWay.WorkerRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker{
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String login;
}
