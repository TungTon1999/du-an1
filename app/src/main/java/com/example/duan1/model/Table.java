package com.example.duan1.model;

public class Table {
    public Integer chair,room;
    public String time;



    public Table(){

    }

    public Table(Integer chair, Integer room, String time) {
        this.chair = chair;
        this.room = room;
        this.time = time;
    }

    public Integer getChair() {
        return chair;
    }

    public void setChair(Integer chair) {
        this.chair = chair;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
