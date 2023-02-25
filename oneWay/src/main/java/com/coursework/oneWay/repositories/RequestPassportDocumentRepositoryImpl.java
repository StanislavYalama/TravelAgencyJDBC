package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.RequestPassportDocument;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestPassportDocumentRepositoryImpl extends JDBCCustomRepositoryImpl<RequestPassportDocument, Integer> implements RequestPassportDocumentRepository{
    public List<RequestPassportDocument> findByRequestId(int requestId, Connection connection) {
        String query = """
                SELECT id, request_passport_id, document_id, path FROM request_passport_document
                WHERE request_passport_id IN (SELECT id FROM request_passport
                WHERE request_id = ?)""";
        List<RequestPassportDocument> requestPassportDocumentList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                RequestPassportDocument requestPassportDocument = new RequestPassportDocument();
                requestPassportDocument.setId(resultSet.getInt(1));
                requestPassportDocument.setRequestPassportId(resultSet.getInt(2));
                requestPassportDocument.setDocumentId(resultSet.getInt(3));
                requestPassportDocument.setPath(resultSet.getString(4));
                requestPassportDocumentList.add(requestPassportDocument);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestPassportDocumentList;
    }
}
