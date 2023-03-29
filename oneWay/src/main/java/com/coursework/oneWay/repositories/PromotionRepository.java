package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Promotion;

import java.sql.Connection;

public interface PromotionRepository {
    void deleteFromTourPromotion(int tourId, int promotionId, Connection connection);
}
