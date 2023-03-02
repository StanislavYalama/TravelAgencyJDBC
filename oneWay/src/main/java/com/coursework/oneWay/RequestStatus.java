package com.coursework.oneWay;

public enum RequestStatus {
    ВІДПРАВЛЕНО, БРОНЮВАННЯ, ПРИЙНЯТО, СКАСОВАНО_КЛІЄНТОМ, СКАСОВАНО_АГЕНСТВОМ;

    public String toDBStatus(){
        return this.name().replace('_', ' ').toLowerCase();
    }
}