package com.coursework.oneWay;

public enum TourType {
    АВТОБУСНИЙ, АВІА;

    public String toDBFormat(){
        return this.name().toLowerCase();
    }
}
