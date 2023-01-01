package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Passport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PassportRepositoryImpl extends JDBCCustomRepositoryImpl<Passport, Integer>{


    public List<Passport> findByRequestId(int requestId, Connection connection) {
        String query = """
                SELECT * FROM request_passport
                WHERE request_id = ?
                """;
        List<Passport> passportList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Passport passport = new Passport();
                passport.setId(resultSet.getInt("id"));
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
}
