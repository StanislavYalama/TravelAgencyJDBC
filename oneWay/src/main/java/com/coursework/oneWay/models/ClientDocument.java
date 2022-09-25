package com.coursework.oneWay.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDocument {

    private int id;
    private int documentId;
    private int clientId;
    private String photoPath;
}
