package com.coursework.oneWay.services;

import com.coursework.oneWay.models.functions.*;
import com.coursework.oneWay.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class MatViewService {

    @Autowired
    private MatViewClientRankRepository matViewClientRankRepository;
    @Autowired
    private MatViewManagerRankRepository matViewManagerRankRepository;
    @Autowired
    private MatViewTourManagerRankRepository matViewTourManagerRankRepository;
    @Autowired
    private MatViewTourRankRepository matViewTourRankRepository;
    @Autowired
    private MatViewTourProfitRepository matViewTourProfitRepository;

    public List<ClientRank> showClientRank(Connection connection){
        return matViewClientRankRepository.show(connection);
    }

    public void refreshClientRank(Connection connection){
        matViewClientRankRepository.refresh(connection);
    }

    public List<ManagerRank> showManagerRank(Connection connection){
        return matViewManagerRankRepository.show(connection);
    }

    public void refreshManagerRank(Connection connection){
        matViewManagerRankRepository.refresh(connection);
    }

    public List<TourManagerRank> showTourManagerRank(Connection connection){
        return matViewTourManagerRankRepository.show(connection);
    }

    public void refreshTourManagerRank(Connection connection){
        matViewTourManagerRankRepository.refresh(connection);
    }

    public List<TourProfit> showTourProfit(Connection connection){
        return matViewTourProfitRepository.show(connection);
    }

    public void refreshTourProfit(Connection connection){
        matViewTourProfitRepository.refresh(connection);
    }

    public List<TourRank> showTourRank(Connection connection){
        return matViewTourRankRepository.show(connection);
    }

    public void refreshTourRank(Connection connection){
        matViewTourRankRepository.refresh(connection);
    }

}
