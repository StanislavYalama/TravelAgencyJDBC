package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTourDocument {
    private Integer id;
    private Integer requestId;
    private Integer tourDocumentId;
    private String path;
    private Integer memberNumber;
}
