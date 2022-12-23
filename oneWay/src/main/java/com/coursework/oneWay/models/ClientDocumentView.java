package com.coursework.oneWay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDocumentView {

    private Integer clientDocumentId;
    private Integer documentId;
    private Integer clientId;
    private String name;
    private String photoPath;
}
