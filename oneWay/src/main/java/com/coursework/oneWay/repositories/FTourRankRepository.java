package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.functions.TourProfit;
import com.coursework.oneWay.models.functions.TourRank;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class FTourRankRepository {
    public List<TourRank> call(Connection connection) {
        List<TourRank> rankList = new ArrayList<>();
        String query = "SELECT * FROM tour_rank()";

        try(Statement statement = connection.createStatement()){
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                TourRank tourRank = new TourRank();
                tourRank.setId(resultSet.getInt(1));
                tourRank.setRequest(resultSet.getInt(2));
                rankList.add(tourRank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankList;
    }
}
