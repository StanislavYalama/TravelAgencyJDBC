package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Promotion;
import com.coursework.oneWay.models.TourPromotion;
import com.coursework.oneWay.repositories.PromotionRepositoryImpl;
import com.coursework.oneWay.repositories.TourPromotionRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepositoryImpl promotionRepository;
    @Autowired
    private TourPromotionRepositoryImpl tourPromotionRepository;

    public List<Promotion> findAll(Connection connection){
        return promotionRepository.findAll(Promotion.class, connection);
    }
    public void save(Promotion promotion, Connection connection){
        promotionRepository.save(promotion, connection);
    }
    public void delete(Integer id, Connection connection){
        promotionRepository.deleteById(Promotion.class, id, connection);
    }
    public Promotion findById(int promotionId, Connection connection) {
        return promotionRepository.findById(Promotion.class, promotionId, connection);
    }
    public void linkWithTour(int promotionId, int tourId, Connection connection){
        tourPromotionRepository.save(new TourPromotion(0, tourId, promotionId), connection);
    }
    public List<Promotion> findByTourId(int tourId, Connection connection){
        return promotionRepository.findByTourId(tourId, connection);
    }
}
