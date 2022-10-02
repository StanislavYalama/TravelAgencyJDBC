package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.functions.ManagerRank;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class FManagerRankRepository {
    public List<ManagerRank> call(Connection connection) {
        List<ManagerRank> rankList = new ArrayList<>();
        String query = "SELECT * FROM manager_rank()";

        try(Statement statement = connection.createStatement()){
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                ManagerRank tourRank = new ManagerRank();
                tourRank.setId(resultSet.getInt(1));
                tourRank.setName(resultSet.getString(2));
                tourRank.setRequests(resultSet.getInt(3));
                tourRank.setProfit(resultSet.getBigDecimal(4));
                rankList.add(tourRank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankList;
    }
}
