package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.RequestTourDocument;
import org.springframework.stereotype.Component;

@Component
public class RequestTourDocumentRepositoryImpl extends JDBCCustomRepositoryImpl<RequestTourDocument, Integer>
        implements RequestTourDocumentRepository {
}
