package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.repositories.RequestRepository;
import com.coursework.oneWay.repositories.RequestRepositoryImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RequestService {

    @Autowired
    private RequestRepositoryImpl requestRepository;

    public List<Request> findAll(Connection connection){
        return requestRepository.findAll(Request.class, connection);
    }

    public Request findById(int requestId, Connection connection){
        return requestRepository.findById(Request.class, requestId, connection);
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

    public int save(int clientId, int tourId, Connection connection){
        requestRepository.save(new Request(0, clientId, "ВІДПРАВЛЕНО", LocalDateTime.now(), tourId, null, false), connection);
        return requestRepository.getCurrentRequestIdSequenceValue(connection);
    }

    public void deleteById(Integer id, Connection connection){
        requestRepository.deleteById(Request.class, id, connection);
    }

    public void setStatus(int id, String newStatus, Integer managerId, Connection connection){
        requestRepository.update(Request.class, id, "status", newStatus, connection);
        requestRepository.update(Request.class, id, "manager_id", managerId, connection);
        log.info("Request status with id {} was updated to {} by manager {}", id, newStatus, managerId);
    }

    public void pay(int requestId, int clientId, Connection connection) {
        requestRepository.pay(requestId, clientId, connection);
    }

//    public void saveNew(int clientId, int tourId){
//        requestRepository.saveNew(clientId, tourId);
//    }
}
