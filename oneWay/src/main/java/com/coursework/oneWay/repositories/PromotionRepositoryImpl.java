package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Promotion;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PromotionRepositoryImpl extends JDBCCustomRepositoryImpl<Promotion, Integer> implements PromotionRepository{

    public List<Promotion> findByTourId(int tourId, Connection connection) {
        List<Promotion> promotionList = new ArrayList<>();
        String query = """
                SELECT * FROM promotion
                WHERE id IN(SELECT promotion_id FROM tour_promotion
                WHERE tour_id = ?)
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Promotion promotion = new Promotion();
                promotion.setId(resultSet.getInt("id"));
                promotion.setDateBeginning(resultSet.getObject("date_beginning", LocalDateTime.class));
                promotion.setDateEnd(resultSet.getObject("date_end", LocalDateTime.class));
                promotion.setWorkerId(resultSet.getInt("worker_id"));
                promotion.setDiscountPercentage(resultSet.getInt("discount_percentage"));
                promotionList.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promotionList;
    }

    @Override
    public void deleteFromTourPromotion(int tourId, int promotionId, Connection connection) {
        String query = "DELETE FROM tour_promotion WHERE tour_id = ? AND promotion_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            preparedStatement.setInt(2, promotionId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
