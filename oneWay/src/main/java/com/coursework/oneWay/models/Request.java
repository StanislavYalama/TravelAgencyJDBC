package com.coursework.oneWay.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private Integer id;
    private Integer clientId;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Integer tourId;
    private Integer workerId;
    private boolean paymentStatus;

}
