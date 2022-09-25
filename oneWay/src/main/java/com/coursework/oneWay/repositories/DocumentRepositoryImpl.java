package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentRepositoryImpl extends JDBCCustomRepositoryImpl<Document, Integer> implements DocumentRepository{

}
