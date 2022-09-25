//package com.coursework.oneWay.services;
//
//import com.tourAgency.oneWay.models.TourPromotion;
//import com.tourAgency.oneWay.repositories.TourPromotionRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class TourPromotionService {
//
//    private final TourPromotionRepository tourPromotionRepository;
//
//    public List<TourPromotion> findAll(){
//        return tourPromotionRepository.findAll();
//    }
//
//    public void save(TourPromotion tourPromotion){
//        log.info("Save new TourPromotion{}", tourPromotion);
//        tourPromotionRepository.save(tourPromotion);
//    }
//}
