package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.repositories.RequestRepository;
import com.coursework.oneWay.repositories.RequestRepositoryImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepositoryImpl requestRepository;

    public List<Request> findAll(Connection connection){
        return requestRepository.findAll(Request.class, connection);
    }

    public List<Request> findUnadmitted(Connection connection){
        return requestRepository.findUnadmitted(connection);
    }

    public List<Request> findAdmitted(Connection connection){
        return requestRepository.findAdmitted(connection);
    }

    public List<Request> findByClientId(int id, Connection connection) {
        return requestRepository.findByClientId(id, connection);
    }

    public void save(Request request, Connection connection){
        requestRepository.save(request, connection);
    }

    public void save(int clientId, int tourId, Connection connection){
        requestRepository.save(new Request(0, clientId, "відпралено", LocalDateTime.now(), tourId, null, false), connection);
    }

    public void deleteById(Integer id, Connection connection){
        requestRepository.deleteById(Request.class, id, connection);
    }

    public void setStatus(int id, String value, Connection connection){
        requestRepository.update(Request.class, id, "status", value, connection);
        log.info("Request status with id {} was updated to {}", id, value);
    }

//    public void saveNew(int clientId, int tourId){
//        requestRepository.saveNew(clientId, tourId);
//    }
}
