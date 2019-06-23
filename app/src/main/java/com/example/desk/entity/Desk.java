package com.example.desk.entity;

public class Desk {

    public Desk(String seatid, String state) {
        this.seatid = seatid;
        this.state = state;
    }

    /**
     * seatid : 1
     * location : 东区
     * classroom : 101
     * seatnumber : 1
     * state : a
     */

    private String seatid;
    private String location;
    private String classroom;
    private int seatnumber;
    private String state;

    public String getSeatid() {
        return seatid;
    }

    public void setSeatid(String seatid) {
        this.seatid = seatid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(int seatnumber) {
        this.seatnumber = seatnumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
