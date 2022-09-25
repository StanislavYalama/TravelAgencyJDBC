package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourDocumentView;

import java.sql.Connection;
import java.util.List;

public interface TourDocumentRepository {
    List<TourDocumentView> findTourDocumentById(int tourId, Connection connection);
}
