package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport {

    private Integer id;
    private String name;
    private String documentNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfExpiry;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfIssue;

}
