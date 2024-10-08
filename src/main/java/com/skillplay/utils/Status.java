package com.skillplay.utils;

public enum Status {
    ACTIVE("active"),
    ONGOING("ongoing"),
    CANCELLED("cancelled"),
    FINISHED("finished"),
    BANNED("banned"),
    DELETED("deleted");


    private final String status;

     Status(String status) {
        this.status = status;
     }

    public String getCode() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public String get(){
        return this.status;
    }

}
