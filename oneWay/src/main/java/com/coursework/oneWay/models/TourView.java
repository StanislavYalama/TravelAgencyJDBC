package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourView {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
    private Double price;
    private String description;
    private Integer workerId;
    private Integer tourOperatorId;
    private boolean visible;
    private Double pricePromotion;
    private Integer discountPercentage;
}
