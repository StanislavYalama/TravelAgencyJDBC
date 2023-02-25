package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.RequestPassportDocument;

import java.sql.Connection;
import java.util.List;

public interface RequestPassportDocumentRepository{

    List<RequestPassportDocument> findByRequestId(int requestId, Connection connection);
}
