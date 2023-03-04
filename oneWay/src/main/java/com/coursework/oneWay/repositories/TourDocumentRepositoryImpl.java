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
public class TourDocumentRepositoryImpl extends JDBCCustomRepositoryImpl<TourDocument, Integer>
        implements TourDocumentRepository {

    @Override
    public List<TourDocumentView> findByTourId(int tourId, Connection connection) {
        List<TourDocumentView> tourDocumentViewList = new ArrayList<>();
        String query = """
                SELECT * FROM tour_document_view
                WHERE tour_id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                TourDocumentView tourDocumentView = new TourDocumentView();
                tourDocumentView.setTourId(resultSet.getInt("tour_id"));
                tourDocumentView.setDocumentId(resultSet.getInt("document_id"));
                tourDocumentView.setName(resultSet.getString("name"));

                tourDocumentViewList.add(tourDocumentView);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourDocumentViewList;
    }
}
