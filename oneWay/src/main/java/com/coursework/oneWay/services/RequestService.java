package com.coursework.oneWay.services;

import com.coursework.oneWay.enums.RequestStatus;
import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.repositories.RequestRepositoryImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RequestService {

    private static boolean isUpdated = false;
    @Getter
    @Setter
    private static long denyInterval = 1000 * 3600 * 24 * 14;
    @Getter
    @Setter
    private static long updateInterval = 1000 * 3600 * 24;
    @Getter
    @Setter
    private static LocalDateTime dateOfLastUpdate = LocalDateTime.now();
    @Autowired
    private TourService tourService;

    @Autowired
    private RequestRepositoryImpl requestRepository;

    public List<Request> findAll(Connection connection) {
        return requestRepository.findAll(Request.class, connection);
    }

    public Request findById(int requestId, Connection connection) {
        return requestRepository.findById(Request.class, requestId, connection);
    }

    public List<Request> findUnadmitted(Connection connection) {
        return requestRepository.findUnadmitted(connection);
    }

    public List<Request> findAdmitted(Connection connection) {
        return requestRepository.findAdmitted(connection);
    }

    public List<Request> findByClientId(int id, Connection connection) {
        return requestRepository.findByClientId(id, connection);
    }

    public void save(Request request, Connection connection) {
        requestRepository.save(request, connection);
    }

    public int save(int clientId, int tourId, Connection connection) {
        requestRepository.save(new Request(0, clientId, RequestStatus.ВІДПРАВЛЕНО.toDBFormat(), LocalDateTime.now(), tourId, null, false), connection);
        return requestRepository.getCurrentRequestIdSequenceValue(connection);
    }

    public void deleteById(Integer id, Connection connection) {
        requestRepository.deleteById(Request.class, id, connection);
    }

    public void setStatus(int id, String newStatus, Integer managerId, Connection connection) {
        requestRepository.update(Request.class, id, "status", newStatus, connection);
        requestRepository.update(Request.class, id, "worker_id", managerId, connection);
        log.info("Request status with id {} was updated to {} by manager {}", id, newStatus, managerId);
    }

    public void pay(int requestId, int clientId, Connection connection) {
        requestRepository.pay(requestId, clientId, connection);
    }

    public int getMembersCountById(int requestId, Connection connection) {
        return requestRepository.getMembersCountById(requestId, connection);
    }

    public Request findByClientIdAndTourId(int clientId, int tourId, Connection connection) {
        return requestRepository.findByClientIdAndTourId(clientId, tourId, connection);
    }
}

