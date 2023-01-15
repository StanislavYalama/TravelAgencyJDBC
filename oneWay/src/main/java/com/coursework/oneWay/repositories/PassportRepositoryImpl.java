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
public class PassportRepositoryImpl extends JDBCCustomRepositoryImpl<Passport, Integer>{


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
        int passport_id = 0;
        String query = "SELECT last_value FROM passport_id_seq";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                passport_id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passport_id;
    }
}
