package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.PersonalWallet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    int findIdByLogin(String login, Connection connection);

    Client findByRequestId(int requestId, Connection connection);

    void changeBalanceByClientId(int clientId, double newBalance, Connection connection);

    PersonalWallet getPersonalWalletByClientId(int clientId, Connection connection);
}