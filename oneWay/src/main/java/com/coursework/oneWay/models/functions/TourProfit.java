package com.coursework.oneWay.models.functions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TourProfit {

    private Integer id;
    private BigDecimal profit;
}
