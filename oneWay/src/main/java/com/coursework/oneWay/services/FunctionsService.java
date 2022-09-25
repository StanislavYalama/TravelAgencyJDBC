package com.coursework.oneWay.services;

import com.coursework.oneWay.models.functions.*;
import com.coursework.oneWay.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class FunctionsService{

    @Autowired
    private FClientRankRepository fClientRankRepository;
    @Autowired
    private FManagerRankRepository fManagerRankRepository;
    @Autowired
    private FTourManagerRankRepository fTourManagerRankRepository;
    @Autowired
    private FTourRankRepository fTourRankRepository;
    @Autowired
    private FTourProfitRepository fTourProfitRepository;

    public List<ClientRank> callClientRank(Connection connection){
        return fClientRankRepository.call(connection);
    }
    public List<ManagerRank> callManagerRank(Connection connection){
        return fManagerRankRepository.call(connection);
    }
    public List<TourManagerRank> callTourManagerRank(Connection connection){
        return fTourManagerRankRepository.call(connection);
    }
    public List<TourProfit> callTourProfit(Connection connection){
        return fTourProfitRepository.call(connection);
    }
    public List<TourRank> callTourRank(Connection connection){
        return fTourRankRepository.call(connection);
    }

//    public ClientRank findById(Integer id, Connection connection){
//        return fClientRankRepository.findById(ClientRank.class, id, connection);
//    }
}
