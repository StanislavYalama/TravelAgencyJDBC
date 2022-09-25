package com.coursework.oneWay.services;

import com.coursework.oneWay.models.functions.TourProfit;
import com.coursework.oneWay.repositories.FTourProfitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class FTourProfitService {

    @Autowired
    private FTourProfitRepository fTourProfitRepository;

//    public List<TourProfit> findAll(Connection connection){
//        return fTourProfitRepository.findAll(TourProfit.class, connection);
//    }

//    public Optional<FTourProfit> findById(Integer id){
//        return fTourProfitRepository.findById(id);
//    }
}
