package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    int findIdByLogin(String login, Connection connection);

//    @Query(nativeQuery = true, value = "select * from client_rank()")
//    List<Map<String, List>> showRank();

//    @Query(value = "select new com.tourAgency.oneWay.models.FClientRank(client_name, count_visits) from ViewClientRank")
//    Collection<FClientRank> showRank();
}