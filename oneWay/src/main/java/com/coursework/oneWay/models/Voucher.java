package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    private Integer id;
    private Integer tourOperatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String country;
    private String photo;
    private Integer requestId;
}