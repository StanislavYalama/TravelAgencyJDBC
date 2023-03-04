package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourDocument;
import org.springframework.stereotype.Component;

@Component
public class TourDocumentRepositoryImpl extends JDBCCustomRepositoryImpl<TourDocument, Integer>
        implements TourDocumentRepository {

}
