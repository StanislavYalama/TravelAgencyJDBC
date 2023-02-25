package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Passport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PassportRepositoryImpl extends JDBCCustomRepositoryImpl<Passport, Integer> implements PassportRepository {


    public List<Passport> findByRequestId(int requestId, Connection connection) {
        String query = """
                SELECT * FROM passport
                WHERE id IN (SELECT passport_id FROM request_passport WHERE request_id = ?)
                """;
        List<Passport> passportList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Passport passport = new Passport();
                passport.setId(resultSet.getInt("id"));
                passport.setName(resultSet.getString("name"));
                passport.setDocumentNumber(resultSet.getString("document_number"));
                passport.setDateOfExpiry(resultSet.getObject("date_of_expiry", LocalDate.class));
                passport.setDateOfIssue(resultSet.getObject("date_of_issue", LocalDate.class));

                passportList.add(passport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passportList;
    }

    public int getCurrentPassportIdSequenceValue(Connection connection) {
        int passportId = 0;
        String query = "SELECT last_value FROM passport_id_seq";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                passportId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passportId;
    }

    public void updateDataByClientId(Passport passport, int clientId, Connection connection) {
        String query = """
                UPDATE passport
                SET document_number = ?, date_of_expiry = ?, date_of_issue = ?
                WHERE id = (SELECT passport_id FROM client
                WHERE id = ?)""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, passport.getDocumentNumber());
            preparedStatement.setDate(2, java.sql.Date.valueOf(passport.getDateOfExpiry()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(passport.getDateOfIssue()));
            preparedStatement.setInt(4, clientId);
            preparedStatement.executeUpdate();

            log.info("Updated data in table passport:\n{}", passport);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDataByClientId(int clientId, Connection connection){
        String query = """
                UPDATE passport
                SET document_number = ?, date_of_expiry = ?, date_of_issue = ?
                WHERE id = (SELECT passport_id FROM client
                WHERE id = ?)""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, null);
            preparedStatement.setDate(2, null);
            preparedStatement.setDate(3, null);
            preparedStatement.setInt(4, clientId);
            preparedStatement.executeUpdate();

            log.info("Cleared passport data of client with id = {}", clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
