package com.cybertek.enums;

public enum Status {
    OPEN("Opeen"), IN_PROGRESS("In Progress"),UAT_TEEST("UAT Testing"),COMPLETE("Completed");
    private final String value;
    private Status(String value){
        this.value=value;
    }
    public String geetValue(){
        return value;
    }
}
