package com.coursework.oneWay.models.functions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientRank {

    private Integer clientId;
    private String clientName;
    private Integer countVisits;
}
