package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Passport;
import com.coursework.oneWay.repositories.PassportRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class PassportService {

    @Autowired
    private PassportRepositoryImpl passportRepository;

    public List<Passport> findAll(Connection connection){
        return passportRepository.findAll(Passport.class, connection);
    }
    public void save(Passport passport, Connection connection){
        passportRepository.save(passport, connection);
    }
    public void delete(Integer id, Connection connection){
        passportRepository.deleteById(Passport.class, id, connection);
    }
    public List<Passport> findByRequestId(int requestId, Connection connection){
        return passportRepository.findByRequestId(requestId, connection);
    }

    public int getCurrentPassportIdSequenceValue(Connection connection) {
        return passportRepository.getCurrentPassportIdSequenceValue(connection);
    }
}
