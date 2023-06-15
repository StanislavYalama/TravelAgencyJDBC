package com.coursework.oneWay.enums;

public enum DocumentType {
    ДЛЯ_ЗАЯВКИ, ДЛЯ_УЧАСТІ_В_ТУРІ;

    public String toDBFormat(){
        return this.name().replace('_', ' ').toLowerCase();
    }
}
