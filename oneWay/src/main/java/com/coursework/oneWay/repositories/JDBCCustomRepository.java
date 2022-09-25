package com.coursework.oneWay.repositories;

import java.sql.Connection;

public interface JDBCCustomRepository <T, ID> extends ReadOnlyRepository<T, ID> {

    void save(T t, Connection connection);

    void deleteById(Class<T> tClass, ID id, Connection connection);

    void update(Class<T> tClass, ID id, String columnName, String value, Connection connection);
}
