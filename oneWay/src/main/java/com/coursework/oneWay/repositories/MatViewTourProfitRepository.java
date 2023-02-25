package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.functions.TourProfit;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class MatViewTourProfitRepository implements MaterializedViewRepository<TourProfit>{

    @Override
    public List<TourProfit> show(Connection connection) {
        List<TourProfit> profitList = new ArrayList<>();
        String query = "SELECT * FROM tour_profit";

        try(Statement statement = connection.createStatement()){
            statement.execute(query);

            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                TourProfit tourProfit = new TourProfit();
                tourProfit.setId(resultSet.getInt(1));
                tourProfit.setProfit(resultSet.getBigDecimal(2));
                profitList.add(tourProfit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profitList;
    }

    @Override
    public void refresh(Connection connection) {
        String query = "REFRESH MATERIALIZED VIEW tour_profit";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
