package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Document;
import com.coursework.oneWay.models.RequestTourDocument;
import com.coursework.oneWay.models.TourDocument;
import com.coursework.oneWay.models.TourDocumentView;
import com.coursework.oneWay.repositories.DocumentRepositoryImpl;
import com.coursework.oneWay.repositories.RequestTourDocumentRepositoryImpl;
import com.coursework.oneWay.repositories.TourDocumentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepositoryImpl documentRepository;
    @Autowired
    private TourDocumentRepositoryImpl tourDocumentRepository;
    @Autowired
    private RequestTourDocumentRepositoryImpl requestTourDocumentRepository;

    public List<Document> findAll(Connection connection){
        return documentRepository.findAll(Document.class, connection);
    }

    public void save(Document document, Connection connection){
        documentRepository.save(document, connection);
    }

    public void saveTourDocumentList(List<TourDocument> tourDocumentList, Connection connection){
        tourDocumentList.forEach(el -> tourDocumentRepository.save(el, connection));
    }

    public List<TourDocumentView> findTourDocumentByTourId(int tourId, Connection connection) {
        return tourDocumentRepository.findByTourId(tourId, connection);
    }

    public void saveRequestTourDocument(RequestTourDocument requestTourDocument, Connection connection) {
        requestTourDocumentRepository.save(requestTourDocument, connection);
    }

    public void deleteTourDocumentByTourId(int tourId, Connection connection) {
        tourDocumentRepository.deleteByTourId(tourId, connection);
    }
}
