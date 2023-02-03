package com.coursework.oneWay.services;

import com.coursework.oneWay.models.TourOperator;
import com.coursework.oneWay.repositories.TourOperatorRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class TourOperatorService {

    @Autowired
    private TourOperatorRepositoryImpl tourOperatorRepository;

    public TourOperator findById(int tourOperatorId, Connection connectionn){
        return tourOperatorRepository.findById(TourOperator.class, tourOperatorId, connectionn);
    }
}
