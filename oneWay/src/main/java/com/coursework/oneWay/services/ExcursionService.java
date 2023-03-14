package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Excursion;
import com.coursework.oneWay.repositories.ExcursionRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class ExcursionService {

    @Autowired
    private ExcursionRepositoryImpl excursionRepository;

    public List<Excursion> findByTourId(int id, Connection connection){
        return excursionRepository.findByTourId(id, connection);
    }

    public List<Excursion> findByTourIdUnspent(int id, Connection connection){
        return excursionRepository.findByTourIdUnspent(id, connection);
    }

    public void save(Excursion excursion, Connection connection) {
        excursionRepository.save(excursion, connection);
    }
}
