package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourOperator;
import org.springframework.stereotype.Component;

@Component
public class TourOperatorRepositoryImpl extends JDBCCustomRepositoryImpl<TourOperator, Integer> implements TourOperatorRepository{

}