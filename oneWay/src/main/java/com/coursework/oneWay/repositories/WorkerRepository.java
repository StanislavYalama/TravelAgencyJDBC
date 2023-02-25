package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Worker;

import java.sql.Connection;

public interface WorkerRepository {

    void saveNewWorker(Worker worker, String password, Connection connection);

    int findIdByLogin(String login, Connection connection);
}
