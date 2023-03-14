package com.coursework.oneWay;

public enum WorkerRole {
    MANAGER, TOUR_MANAGER, ADMINISTRATOR;

    public String toDBFormat(){
        return this.name().toLowerCase();
    }
}
