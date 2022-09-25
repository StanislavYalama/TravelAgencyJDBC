package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.functions.TourManagerRank;
import com.coursework.oneWay.models.functions.TourRank;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class FTourManagerRankRepository {
    public List<TourManagerRank> call(Connection connection) {
        List<TourManagerRank> rankList = new ArrayList<>();
        String query = "SELECT * FROM tour_manager_rank()";

        try(Statement statement = connection.createStatement()){
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                TourManagerRank tourRank = new TourManagerRank();
                tourRank.setId(resultSet.getInt(1));
                tourRank.setName(resultSet.getString(2));
                tourRank.setCreatedTours(resultSet.getInt(3));
                rankList.add(tourRank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankList;
    }
}
