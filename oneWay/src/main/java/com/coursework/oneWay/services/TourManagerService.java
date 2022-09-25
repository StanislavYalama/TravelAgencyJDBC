package com.coursework.oneWay.services;

import com.coursework.oneWay.models.TourManager;
import com.coursework.oneWay.repositories.TourManagerRepository;
import com.coursework.oneWay.repositories.TourManagerRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourManagerService {

    private final TourManagerRepositoryImpl tourManagerRepository;

    public int findIdByLogin(String login, Connection connection){
        return tourManagerRepository.findIdByLogin(login, connection);
    }
//    public List<TourManager> findAll(){
//        return tourManagerRepository.findAll();
//    }
//
//    public TourManager findByEmail(String email){
//        return tourManagerRepository.findByEmail(email);
//    }
//
//    public TourManager findById(Integer id){
//        return tourManagerRepository.getById(id);
//    }
//
//    public void save(TourManager tourManager){
//        log.info("Save new tour manager{}", tourManager);
//        tourManagerRepository.save(tourManager);
//    }
//
//    public void delete(Integer id){
//        log.info("Deleted tour manager with id{}", id);
//        tourManagerRepository.deleteById(id);
//    }
//    public List<FTourManagerRank> showRank(){
//        return tourManagerRepository.showTourManagerRank();
//    }
}