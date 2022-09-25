package com.coursework.oneWay.services;

import com.coursework.oneWay.models.functions.TourRank;
import com.coursework.oneWay.repositories.FTourRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class FTourRankService {

    @Autowired
    private FTourRankRepository fTourRankRepository;

//    public List<TourRank> findAll(Connection connection){
//        return fTourRankRepository.findAll(TourRank.class, connection);
//    }

//    public FTourRank findById(Integer id, Connection connection){
//        return fTourRankRepository.findById(FTourRank.class, id, connection);
//    }
}
