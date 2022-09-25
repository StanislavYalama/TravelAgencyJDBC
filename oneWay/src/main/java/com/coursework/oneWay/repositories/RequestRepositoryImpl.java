package com.coursework.oneWay.repositories;

import com.coursework.oneWay.STATUS;
import com.coursework.oneWay.models.Request;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestRepositoryImpl extends JDBCCustomRepositoryImpl<Request, Integer> implements RequestRepository{

    //            value = "select * from Request r where r.status = 'відправлено' ",
    @Override
    public List<Request> findUnadmitted(Connection connection) {
        List<Request> requestList = findAll(Request.class, connection);
        requestList.removeIf(el -> !el.getStatus().equals("відправлено"));

        return requestList;
    }

//            value = "select * from Request r where r.status != 'відправлено' ",
    @Override
    public List<Request> findAdmitted(Connection connection) {
        List<Request> requestList = findAll(Request.class, connection);
        requestList.removeIf(el -> el.getStatus().equals("відправлено"));

        return requestList;
    }

//            value = "select * from Request r where r.client_id = ?1 ",
    @Override
    public List<Request> findByClientId(int id, Connection connection) {
        List<Request> requestList = findAll(Request.class, connection);
        requestList.removeIf(el -> el.getClientId() != id);

        return requestList;
    }
}
