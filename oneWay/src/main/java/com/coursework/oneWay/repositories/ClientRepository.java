package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    List<Client> findAll(Connection connection);

    Client findById(int id, Connection connection);

    int findIdByLogin(String login, Connection connection);

    void save(Client client, Connection connection);

    void deleteById(int id, Connection connection);

    void updatePassportId(int clientId, int passportId, Connection connection);
//    @Query(nativeQuery = true, value = "select * from client_rank()")
//    List<Map<String, List>> showRank();

//    @Query(value = "select new com.tourAgency.oneWay.models.FClientRank(client_name, count_visits) from ViewClientRank")
//    Collection<FClientRank> showRank();
}