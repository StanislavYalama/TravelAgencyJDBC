package com.coursework.oneWay.models;

import lombok.*;

import java.time.LocalDateTime;

//@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    private Integer id;
    private LocalDateTime dateBeginning;
    private LocalDateTime dateEnd;
    private Integer workerId;
    private Integer discountPercentage;
}