package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.ClientDocument;
import com.coursework.oneWay.models.ClientDocumentView;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientDocumentRepositoryImpl
        extends JDBCCustomRepositoryImpl<ClientDocument, Integer> implements ClientDocumentRepository{

    @Override
    public List<ClientDocumentView> findByClientId(int clientId, Connection connection) {
        String query = """
                SELECT cd.client_id, d.name, cd.photo_path FROM document d
                LEFT JOIN client_document cd ON d.id=cd.document_id
                WHERE client_id = ?
                """;
        List<ClientDocumentView> viewList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ClientDocumentView clientDocumentView = new ClientDocumentView();
                clientDocumentView.setClientId(resultSet.getInt("client_id"));
                clientDocumentView.setName(resultSet.getString("name"));
                clientDocumentView.setPhotoPath(resultSet.getString("photo_path"));
                viewList.add(clientDocumentView);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewList;
    }
}
