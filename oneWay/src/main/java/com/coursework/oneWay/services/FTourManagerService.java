package com.coursework.oneWay.services;

import com.coursework.oneWay.models.functions.TourManagerRank;
import com.coursework.oneWay.repositories.FTourManagerRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class FTourManagerService {

    @Autowired
    private FTourManagerRankRepository fTourManagerRankRepository;

//    public List<TourManagerRank> findAll(Connection connection){
//        return fTourManagerRankRepository.findAll(TourManagerRank.class, connection);
//    }

//    public Optional<FTourManagerRank> findById(Integer id){
//        return fTourManagerRankRepository.findById(id);
//    }
}
