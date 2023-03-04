package com.coursework.oneWay;

public enum DocumentType {
    ДЛЯ_ЗАЯВКИ, ДЛЯ_УЧАСТІ_В_ТУРІ;

    public String toDBStatus(){
        return this.name().replace('_', ' ').toLowerCase();
    }
}
