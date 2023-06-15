package com.coursework.oneWay.enums;

public enum TourType {
    АВТОБУСНИЙ, АВІА;

    public String toDBFormat(){
        return this.name().toLowerCase();
    }
}
