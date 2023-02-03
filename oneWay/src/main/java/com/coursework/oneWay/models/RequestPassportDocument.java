package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPassportDocument {

    private Integer id;
    private Integer requestPassportId;
    private Integer documentId;
    private String path;
}

