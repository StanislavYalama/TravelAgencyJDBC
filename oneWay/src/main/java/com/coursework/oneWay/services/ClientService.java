package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.models.ClientDocumentView;
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
        return clientRepository.findAll(connection);
    }

    public Client findById(Integer id, Connection connection){
        return clientRepository.findById(id, connection);
    }
//    public Client findByEmail(String email) throws SQLException {
//        return clientRepository.findByEmail(email);
//    }

    public void save(Client client, Connection connection){
        clientRepository.save(client, connection);
    }
    public void delete(Integer id, Connection connection){
        clientRepository.deleteById(id, connection);
    }

    public void updatePassportId(int clientId, int passportId, Connection connection){
        clientRepository.updatePassportId(clientId, passportId, connection);
    }

    public Client findByRequestId(int requestId, Connection connection){
        return clientRepository.findByRequestId(requestId, connection);
    }

    public void changeBalanceByClientId(int clientId, double newBalance, Connection connection) {
        clientRepository.changeBalanceByClientId(clientId, newBalance, connection);
    }
//    public Collection<FClientRank> showRank() {
//        return clientRepository.showRank();
//    }
}