package com.coursework.oneWay.services;

import com.coursework.oneWay.Status;
import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.repositories.RequestRepository;
import com.coursework.oneWay.repositories.RequestRepositoryImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
        List<Request> requestList = requestRepository.findAll(Request.class, connection);

        checkDenyStatus(requestList, connection);
        return requestList;
    }

    public Request findById(int requestId, Connection connection) {
        Request requestList = requestRepository.findById(Request.class, requestId, connection);

        checkDenyStatus(requestList, connection);
        return requestList;
    }

    public List<Request> findUnadmitted(Connection connection) {
        List<Request> requestList = requestRepository.findUnadmitted(connection);

        checkDenyStatus(requestList, connection);
        return requestList;
    }

    public List<Request> findAdmitted(Connection connection) {
        List<Request> requestList = requestRepository.findAdmitted(connection);

        checkDenyStatus(requestList, connection);
        return requestList;
    }

    public List<Request> findByClientId(int id, Connection connection) {
        List<Request> requestList = requestRepository.findByClientId(id, connection);

        checkDenyStatus(requestList, connection);
        return requestList;
    }

    public void save(Request request, Connection connection) {
        requestRepository.save(request, connection);
    }

    public int save(int clientId, int tourId, Connection connection) {
        requestRepository.save(new Request(0, clientId, "ВІДПРАВЛЕНО", LocalDateTime.now(), tourId, null, false), connection);
        return requestRepository.getCurrentRequestIdSequenceValue(connection);
    }

    public void deleteById(Integer id, Connection connection) {
        requestRepository.deleteById(Request.class, id, connection);
    }

    public void setStatus(int id, String newStatus, Integer managerId, Connection connection) {
        requestRepository.update(Request.class, id, "status", newStatus, connection);
        requestRepository.update(Request.class, id, "manager_id", managerId, connection);
        log.info("Request status with id {} was updated to {} by manager {}", id, newStatus, managerId);
    }

    public void pay(int requestId, int clientId, Connection connection) {
        requestRepository.pay(requestId, clientId, connection);
    }

    private void checkDenyStatus(List<Request> requestList, Connection connection) {
        if (Duration.between(LocalDate.now(), dateOfLastUpdate).toMillis() > updateInterval) {
            isUpdated = false;
        }
        if (!isUpdated) {
            requestList.forEach(el -> {
                if(el.getStatus().equals(Status.ВІДПРАВЛЕНО.toDBStatus())){
                    long duration = Duration.between(
                            tourService.findByRequestId(el.getId(), connection).getDateStart(),
                            LocalDate.now()).toMillis();

                    if (duration < denyInterval) {
                        requestRepository.update(Request.class, el.getId(), "status",
                                Status.СКАСОВАНО_АГЕНСТВОМ.toDBStatus(), connection);
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
            if(request.getStatus().equals(Status.ВІДПРАВЛЕНО.toDBStatus())){
                long duration = Duration.between(
                        tourService.findByRequestId(request.getId(), connection).getDateStart(),
                        LocalDate.now()).toMillis();

                if (duration < denyInterval) {
                    requestRepository.update(Request.class, request.getId(), "status",
                            Status.СКАСОВАНО_АГЕНСТВОМ.toDBStatus(), connection);
                }
            }

            isUpdated = true;
        }
    }
}

//    public void saveNew(int clientId, int tourId){
//        requestRepository.saveNew(clientId, tourId);
//    }

