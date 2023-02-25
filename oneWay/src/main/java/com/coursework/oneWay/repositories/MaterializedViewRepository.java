package com.coursework.oneWay.repositories;

import java.sql.Connection;
import java.util.List;

public interface MaterializedViewRepository<T> {
    List<T> show(Connection connection);

    void refresh(Connection connection);
}
