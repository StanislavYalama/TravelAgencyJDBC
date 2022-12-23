package com.coursework.oneWay.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

//@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    private Integer id;
    private LocalDateTime dateBeginning;
    private LocalDateTime dateEnd;
    private Integer creatorId;
    private Integer discountPercentage;
}