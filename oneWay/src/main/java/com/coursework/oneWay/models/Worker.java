package com.coursework.oneWay.models;

import com.coursework.oneWay.WorkerRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Worker {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private WorkerRole role;
    private String login;
}
