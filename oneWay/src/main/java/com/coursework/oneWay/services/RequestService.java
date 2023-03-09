package com.coursework.oneWay.services;

import com.coursework.oneWay.RequestStatus;
import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.repositories.RequestRepositoryImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
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

//        checkDenyStatus(requestList, connection);
        return requestRepository.findAll(Request.class, connection);
    }

    public Request findById(int requestId, Connection connection) {

//        checkDenyStatus(requestList, connection);
        return requestRepository.findById(Request.class, requestId, connection);
    }

    public List<Request> findUnadmitted(Connection connection) {

//        checkDenyStatus(requestList, connection);
        return requestRepository.findUnadmitted(connection);
    }

    public List<Request> findAdmitted(Connection connection) {

//        checkDenyStatus(requestList, connection);
        return requestRepository.findAdmitted(connection);
    }

    public List<Request> findByClientId(int id, Connection connection) {

//        checkDenyStatus(requestList, connection);
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

    //TODO
    private void checkDenyStatus(List<Request> requestList, Connection connection) {
        if (Duration.between(LocalDate.now(), dateOfLastUpdate).toMillis() > updateInterval) {
            isUpdated = false;
        }
        if (!isUpdated) {
            requestList.forEach(el -> {
                if(el.getStatus().equals(RequestStatus.ВІДПРАВЛЕНО.toDBFormat()) || !el.isPaymentStatus()){
                    long duration = Duration.between(
                            tourService.findByRequestId(el.getId(), connection).getDateStart(),
                            LocalDate.now()).toMillis();

                    if (duration < denyInterval) {
                        requestRepository.update(Request.class, el.getId(), "status",
                                RequestStatus.СКАСОВАНО_АГЕНСТВОМ.toDBFormat(), connection);
                    }
                }
            });

            isUpdated = true;
        }
    }

    private void checkDenyStatus(Request request, Connection connection) {
        if (Duration.between(LocalDate.now(), dateOfLastUpdate).toMillis() > updateInterval) {
            isUpdated = false;
        }
        if (!isUpdated) {
            if(request.getStatus().equals(RequestStatus.ВІДПРАВЛЕНО.toDBFormat()) || !request.isPaymentStatus()){
                long duration = Duration.between(
                        tourService.findByRequestId(request.getId(), connection).getDateStart(),
                        LocalDate.now()).toMillis();

                if (duration < denyInterval) {
                    requestRepository.update(Request.class, request.getId(), "status",
                            RequestStatus.СКАСОВАНО_АГЕНСТВОМ.toDBFormat(), connection);
                }
            }

            isUpdated = true;
        }
    }

    public int getMembersCountById(int requestId, Connection connection) {
        return requestRepository.getMembersCountById(requestId, connection);
    }
}

//    public void saveNew(int clientId, int tourId){
//        requestRepository.saveNew(clientId, tourId);
//    }

