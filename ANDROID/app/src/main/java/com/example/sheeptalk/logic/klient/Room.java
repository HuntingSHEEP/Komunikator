package com.example.sheeptalk.logic.klient;

public class Room {
    private int userID,roomID;

    public Room(int userID,int roomID){
        this.roomID=roomID;
        this.userID=userID;
    }

    public int getRoom(){return roomID;}
    public int getUser(){return userID;}
}
