package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.models.Voucher;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class VoucherRepositoryImpl extends JDBCCustomRepositoryImpl<Voucher, Integer> implements VoucherRepository{

    @Override
    public List<Voucher> findByClientId(Integer id, Connection connection) {
        String queryResult = "SELECT * FROM voucher WHERE request_id IN (SELECT id FROM request WHERE client_id = ?)";
        String queryColumn = "SELECT * FROM get_columns('voucher')";

        List<Voucher> result = new ArrayList<>();
        List<String> columnList = new ArrayList<>();
        List<Method> methodList = Arrays.stream(Voucher.class.getMethods())
                .filter(el -> el.getName().contains("set"))
                .sorted(Comparator.comparing(Method::getName))
                .toList();

        try(Statement statement = connection.createStatement()) {
            // get column list of required entity
            ResultSet rs = statement.executeQuery(queryColumn);
            while (rs.next()) {
                columnList.add(rs.getString(1));
            }

            rs.close();
            // sort this shit
            columnList.sort(Comparator.naturalOrder());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

            // get result data from required entity
        try(PreparedStatement preparedStatement = connection.prepareStatement(queryResult)){
            preparedStatement.setInt(1, id);
            ResultSet rs2 = preparedStatement.executeQuery();
            while(rs2.next()){
                Voucher voucher = new Voucher();
                List<Class<?>[]> paramTypes = new ArrayList<>();

                for (Method method : methodList) {
                    paramTypes.add(method.getParameterTypes());
                }

                for(int i = 0; i < methodList.size(); i++){
                    Class<?> aClass = paramTypes.get(i)[0];
                    if(paramTypes.get(i)[0].getName().equals("java.util.Date")){
                        java.sql.Date sqlDate = (Date) rs2.getObject(columnList.get(i), java.sql.Date.class);
                        methodList.get(i).invoke(voucher, new java.util.Date(sqlDate.getTime()));
                    }
                    else{
                        methodList.get(i).invoke(voucher, rs2.getObject(columnList.get(i), aClass));
                    }
                }
                result.add(voucher);
            }
        }
        catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}
/*
* тааак блт... как достать список ваучер по айди клиента?
* ебу?
* */