package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.RequestPassport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestPassportRepositoryImpl extends JDBCCustomRepositoryImpl<RequestPassport, Integer>{
}
