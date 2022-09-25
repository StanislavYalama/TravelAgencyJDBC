package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Manager;
import com.coursework.oneWay.repositories.ManagerRepository;
import com.coursework.oneWay.repositories.ManagerRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepositoryImpl managerRepository;

    public int findIdByLogin(String login, Connection connection){
        return managerRepository.findIdByLogin(login, connection);
    }

//    public List<Manager> findAll(){
//        return managerRepository.findAll();
//    }
//
//    public Manager findByEmail(String email){
//        return managerRepository.findByEmail(email);
//    }
//
//    public void save(Manager manager){
//        log.info("Save new manager{}", manager);
//        managerRepository.save(manager);
//    }
//    public void delete(Integer id){
//        log.info("Deleted manager with id{}", id);
//        managerRepository.deleteById(id);
//    }
//    public List<FManagerRank> showRank(){
//        return managerRepository.showRank();
//    }
}
