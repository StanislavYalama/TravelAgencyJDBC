package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.functions.ClientRank;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FClientRankRepository {

    public List<ClientRank> call(Connection connection){
        List<ClientRank> rankList = new ArrayList<>();
        String query = "SELECT * FROM client_rank()";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                ClientRank clientRank = new ClientRank();
                clientRank.setClientId(resultSet.getInt(1));
                clientRank.setClientName(resultSet.getString(2));
                clientRank.setCountVisits(resultSet.getInt(3));
                rankList.add(clientRank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rankList;
    }
}
