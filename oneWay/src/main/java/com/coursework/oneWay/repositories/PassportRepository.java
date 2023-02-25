package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Passport;

import java.sql.Connection;

public interface PassportRepository extends JDBCCustomRepository<Passport, Integer>{

    void updateDataByClientId(Passport passport, int clientId, Connection connection);

    int getCurrentPassportIdSequenceValue(Connection connection);
}
