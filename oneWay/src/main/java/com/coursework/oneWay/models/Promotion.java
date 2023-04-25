package com.coursework.oneWay.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateBeginning;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateEnd;
    private Integer workerId;
    private Integer discountPercentage;
}
