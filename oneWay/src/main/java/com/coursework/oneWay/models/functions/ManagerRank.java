package com.coursework.oneWay.models.functions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ManagerRank {

    private Integer id;
    private String name;
    private Integer requests;
    private BigDecimal profit;
}
