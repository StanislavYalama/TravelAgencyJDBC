package com.coursework.oneWay.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDocumentView {

    private int clientId;
    private String name;
    private String photoPath;
}
