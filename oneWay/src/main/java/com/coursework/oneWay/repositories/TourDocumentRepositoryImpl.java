package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.TourDocument;
import com.coursework.oneWay.models.TourDocumentView;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TourDocumentRepositoryImpl implements TourDocumentRepository{

    @Override
    public List<TourDocumentView> findTourDocumentById(int tourId, Connection connection) {
        String query = """
                SELECT td.tour_id, d.name FROM document d
                RIGHT JOIN tour_document td ON d.id=td.document_id
                WHERE tour_id = ?
                """;
        List<TourDocumentView> viewList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                TourDocumentView tourDocumentView = new TourDocumentView();
                tourDocumentView.setTourId(resultSet.getInt("tour_id"));
                tourDocumentView.setName(resultSet.getString("name"));
                viewList.add(tourDocumentView);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewList;
    }
}
