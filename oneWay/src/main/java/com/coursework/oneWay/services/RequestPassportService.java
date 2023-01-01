package com.coursework.oneWay.services;

import com.coursework.oneWay.models.RequestPassport;
import com.coursework.oneWay.repositories.RequestPassportRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class RequestPassportService {

    @Autowired
    private RequestPassportRepositoryImpl requestPassportRepository;

    public void save(RequestPassport requestPassport, Connection connection){
        requestPassportRepository.save(requestPassport, connection);
    }
    public void delete(Integer id, Connection connection){
        requestPassportRepository.deleteById(RequestPassport.class, id, connection);
    }

}
