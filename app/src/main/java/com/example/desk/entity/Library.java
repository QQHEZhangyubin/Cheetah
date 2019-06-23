package com.example.desk.entity;

public class Library {
    private String seatid;//座位id
    private String status;//座位状态

    public Library(String seatid, String status) {
        this.seatid = seatid;
        this.status = status;
    }

    public String getSeatid() {
        return seatid;
    }

    public void setSeatid(String seatid) {
        this.seatid = seatid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
