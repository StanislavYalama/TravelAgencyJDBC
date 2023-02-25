package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Worker;
import com.coursework.oneWay.repositories.WorkerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepositoryImpl workerRepository;

    public List<Worker> findAll(Connection connection){
        return workerRepository.findAll(Worker.class, connection);
    }

    public void save(Worker worker, Connection connection){
        workerRepository.save(worker, connection);
    }

    public int findIdByLogin(String login, Connection connection) {
        return workerRepository.findIdByLogin(login, connection);
    }
}
