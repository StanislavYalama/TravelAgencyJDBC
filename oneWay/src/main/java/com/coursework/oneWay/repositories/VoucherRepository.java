package com.coursework.oneWay.repositories;

import com.coursework.oneWay.models.Voucher;

import java.sql.Connection;
import java.util.List;

public interface VoucherRepository extends JDBCCustomRepository<Voucher, Integer> {
    List<Voucher> findByClientId(Integer id, Connection connection);
}
