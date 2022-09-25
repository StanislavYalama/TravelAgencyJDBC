package com.coursework.oneWay.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourDocument {

    private int id;
    private int tourId;
    private int documentId;
}
