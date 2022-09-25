package com.coursework.oneWay.services;

import com.coursework.oneWay.models.Voucher;
import com.coursework.oneWay.repositories.VoucherRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepositoryImpl voucherRepository;

    public List<Voucher> findAll(Connection connection){
        return voucherRepository.findAll(Voucher.class, connection);
    }

    public Voucher findById(int id, Connection connection){
        return voucherRepository.findById(Voucher.class, id, connection);
    }

    public List<Voucher> findByClientId(int clientId, Connection connection){
        return voucherRepository.findByClientId(clientId, connection);
    }

    public void save(Voucher voucher, Connection connection){
        voucherRepository.save(voucher, connection);
    }

    public void delete(int id, Connection connection){
        voucherRepository.deleteById(Voucher.class, id, connection);
    }
}
