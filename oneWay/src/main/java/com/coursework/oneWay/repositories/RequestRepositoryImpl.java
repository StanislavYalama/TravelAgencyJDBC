package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class RequestRepositoryImpl extends JDBCCustomRepositoryImpl<Request, Integer> implements RequestRepository{

    @Override
    public List<Request> findUnadmitted(Connection connection) {
        List<Request> requestList = findAll(Request.class, connection);
        requestList.removeIf(el -> !el.getStatus().equals("відправлено"));

        return requestList;
    }

    @Override
    public List<Request> findAdmitted(Connection connection) {
        List<Request> requestList = findAll(Request.class, connection);
        requestList.removeIf(el -> el.getStatus().equals("відправлено"));

        return requestList;
    }

    @Override
    public List<Request> findByClientId(int id, Connection connection) {
        List<Request> requestList = findAll(Request.class, connection);
        requestList.removeIf(el -> el.getClientId() != id);

        return requestList;
    }

    @Override
    public void pay(int requestId, int clientId, Connection connection) {

        String tourPriceQuery = """
                SELECT price_promotion FROM tour_view
                WHERE id = (SELECT tour_id FROM request
                    WHERE id = ? AND client_id = ?)""";
        String memberCountQuery = """
                SELECT DISTINCT MAX(member_number) OVER (PARTITION BY request_id) FROM request_tour_document
                WHERE request_id = ?""";
        String walletBalanceQuery = "SELECT balance FROM personal_wallet WHERE client_id = ?";
        String changeBalanceQuery = "UPDATE personal_wallet SET balance = balance - ? WHERE client_id = ?";
        String changePaymentStatus = "UPDATE request SET payment_status = true WHERE id = ?";

        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(tourPriceQuery);
            preparedStatement1.setInt(1, requestId);
            preparedStatement1.setInt(2, clientId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            double price = 0;
            while(resultSet1.next()){
                price = resultSet1.getDouble(1);
            }
            resultSet1.close();
            preparedStatement1.close();

            PreparedStatement preparedStatement = connection.prepareStatement(memberCountQuery);
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int memberCount = 0;
            while(resultSet.next()){
                memberCount = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();

            if(memberCount != 0){
                price *= memberCount;
            }

            int previousLevel = connection.getTransactionIsolation();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement2 = connection.prepareStatement(walletBalanceQuery);
            preparedStatement2.setInt(1, clientId);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            double walletBalance = 0;
            while(resultSet2.next()){
                walletBalance = resultSet2.getDouble(1);
            }
            resultSet2.close();

            if(walletBalance >= price){

                PreparedStatement preparedStatement3 = connection.prepareStatement(changeBalanceQuery);
                preparedStatement3.setDouble(1, price);
                preparedStatement3.setInt(2, clientId);
                preparedStatement3.executeUpdate();
                preparedStatement3.close();

                PreparedStatement preparedStatement4 = connection.prepareStatement(changePaymentStatus);
                preparedStatement4.setInt(1, requestId);
                preparedStatement4.executeUpdate();
                preparedStatement4.close();

                connection.commit();
            } else{
                connection.rollback();
                log.info("Not enough money in personal wallet.");
            }

            connection.setTransactionIsolation(previousLevel);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getMembersCountById(int requestId, Connection connection) {
        int membersCount = 0;
        String query = """
                SELECT DISTINCT MAX(member_number) OVER(PARTITION BY request_id)
                FROM request_tour_document
                WHERE request_id = ?""";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, requestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            membersCount = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersCount;
    }

    @Override
    public Request findByClientIdAndTourId(int clientId, int tourId, Connection connection) {
        Request request = new Request();
        String query = """
                SELECT * FROM request
                WHERE client_id = ? AND tour_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, tourId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                request.setId(resultSet.getInt("id"));
                request.setClientId(resultSet.getInt("client_id"));
                request.setTourId(resultSet.getInt("tour_id"));
                request.setStatus(resultSet.getString("status"));
                request.setDate(resultSet.getObject("date", LocalDateTime.class));
                request.setPaymentStatus(resultSet.getBoolean("payment_status"));
                request.setWorkerId(resultSet.getInt("worker_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return request;
    }

    public int getCurrentRequestIdSequenceValue(Connection connection) {
        int requestId = 0;
        String query = "SELECT last_value FROM request_id_seq";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                requestId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestId;
    }
}
