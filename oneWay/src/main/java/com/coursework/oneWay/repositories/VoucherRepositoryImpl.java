package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.models.Voucher;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class VoucherRepositoryImpl extends JDBCCustomRepositoryImpl<Voucher, Integer> implements VoucherRepository{

    @Override
    public List<Voucher> findByClientId(Integer id, Connection connection) {
        String queryResult = "SELECT * FROM voucher WHERE request_id IN (SELECT id FROM request WHERE client_id = ?)";

        List<Voucher> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryResult)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Voucher voucher = new Voucher();
                voucher.setId(resultSet.getInt("id"));
                voucher.setCountry(resultSet.getString("country"));
                LocalDate localDate = resultSet.getObject("date", LocalDate.class);
                voucher.setDate(localDate);
                voucher.setRequestId(resultSet.getInt("request_id"));
                voucher.setTourOperatorId(resultSet.getInt("tour_operator_id"));
                voucher.setPhoto(resultSet.getString("photo"));
                result.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}