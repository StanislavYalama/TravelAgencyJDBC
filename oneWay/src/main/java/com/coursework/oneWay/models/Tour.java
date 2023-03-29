package com.coursework.oneWay.models;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
    private Integer id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
    private Double price;
    private String description;
    private Integer workerId;
    private Integer tourOperatorId;
    private boolean visible;
    private String type;
    private Integer numberOfSeats;
}
