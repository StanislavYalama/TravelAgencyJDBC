package com.coursework.oneWay.services;

import com.coursework.oneWay.models.functions.ManagerRank;
import com.coursework.oneWay.repositories.FManagerRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class FManagerService {

    @Autowired
    private FManagerRankRepository fManagerRankRepository;

//    public List<ManagerRank> findAll(Connection connection){
//        return fManagerRankRepository.findAll(ManagerRank.class, connection);
//    }

//    public Optional<FManagerRank> findById(Integer id){
//        return fManagerRankRepository.findById(id);
//    }
}
