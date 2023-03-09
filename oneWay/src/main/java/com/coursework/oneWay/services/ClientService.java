package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.ClientDocumentView;
import com.coursework.oneWay.models.PersonalWallet;
import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.repositories.ClientRepository;
import com.coursework.oneWay.repositories.ClientRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepositoryImpl clientRepository;

    public int findIdByLogin(String login, Connection connection){
        return clientRepository.findIdByLogin(login, connection);
    }

    public List<Client> findAll(Connection connection) throws SQLException {
        return clientRepository.findAll(Client.class, connection);
    }

    public Client findById(Integer id, Connection connection){
        return clientRepository.findById(Client.class, id, connection);
    }

    public void save(Client client, Connection connection){
        clientRepository.save(client, connection);
    }
    public void delete(Integer id, Connection connection){
        clientRepository.deleteById(Client.class, id, connection);
    }

    public Client findByRequestId(int requestId, Connection connection){
        return clientRepository.findByRequestId(requestId, connection);
    }

    public void changeBalanceByClientId(int clientId, double newBalance, Connection connection) {
        clientRepository.changeBalanceByClientId(clientId, newBalance, connection);
    }

    public PersonalWallet getPersonalWalletByClinetId(int clientId, Connection connection){
        return clientRepository.getPersonalWalletByClientId(clientId, connection);
    }
}