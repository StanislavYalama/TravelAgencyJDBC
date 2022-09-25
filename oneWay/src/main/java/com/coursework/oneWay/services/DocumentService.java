package com.coursework.oneWay.services;

import com.coursework.oneWay.models.ClientDocument;
import com.coursework.oneWay.models.ClientDocumentView;
import com.coursework.oneWay.models.TourDocument;
import com.coursework.oneWay.models.TourDocumentView;
import com.coursework.oneWay.repositories.ClientDocumentRepositoryImpl;
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
    @Autowired
    private ClientDocumentRepositoryImpl clientDocumentRepository;

//    public List<TourDocument> findAllTourDocument(Connection connection){
//        return tourDocumentRepository.findAll(TourDocument.class, connection);
//    }
    public List<TourDocumentView> findTourDocumentByTourId(int tourId, Connection connection){
        return tourDocumentRepository.findTourDocumentById(tourId, connection);
    }
    public List<ClientDocumentView> findClientDocumentByClientId(int clientId, Connection connection){
        return clientDocumentRepository.findByClientId(clientId, connection);
    }
    public void save(TourDocument tourDocument, Connection connection){
        tourDocumentRepository.save(tourDocument, connection);
    }
    public void save(ClientDocument clientDocument, Connection connection){
        clientDocumentRepository.save(clientDocument, connection);
    }

}
