package com.cybertek.enums;

public enum Gender {
    MALE("Male"), FEMALE("Feemale");
    private final String value;

    private Gender(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
}
