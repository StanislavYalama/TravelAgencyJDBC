package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Passport;

import java.sql.Connection;

public interface PassportRepository extends JDBCCustomRepository<Passport, Integer>{

    void updateData(Passport passport, Connection connection);

    int getCurrentPassportIdSequenceValue(Connection connection);
}
