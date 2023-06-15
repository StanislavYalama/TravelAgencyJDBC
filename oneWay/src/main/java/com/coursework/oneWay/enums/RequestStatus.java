package com.coursework.oneWay.enums;

public enum RequestStatus {
    ВІДПРАВЛЕНО, БРОНЮВАННЯ, ПРИЙНЯТО, СКАСОВАНО_КЛІЄНТОМ, СКАСОВАНО_АГЕНСТВОМ;

    public String toDBFormat(){
        return this.name().replace('_', ' ').toLowerCase();
    }
}