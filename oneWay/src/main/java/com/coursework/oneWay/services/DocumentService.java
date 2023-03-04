package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Document;
import com.coursework.oneWay.models.TourDocument;
import com.coursework.oneWay.repositories.DocumentRepositoryImpl;
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

    public void save(Document document, Connection connection){
        documentRepository.save(document, connection);
    }

    public void saveTourDocumentList(List<TourDocument> tourDocumentList, Connection connection){
        tourDocumentList.forEach(el -> tourDocumentRepository.save(el, connection));
    }

}
