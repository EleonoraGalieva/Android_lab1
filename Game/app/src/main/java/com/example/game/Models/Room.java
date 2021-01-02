package com.example.game.Models;

public class Room {
    private String id;
    private String roomName;
    private String player1; // main user, who creates room
    private String player2;
    private Game game1;
    private Game game2;

    public Room() {
    }

    public Room(String id, String roomName, String player1, String player2, Game game1, Game game2) {
        this.id = id;
        this.roomName = roomName;
        this.player1 = player1;
        this.player2 = player2;
        this.game1 = game1;
        this.game2 = game2;
    }

    public Room(String roomName, String player1, String player2, Game game1, Game game2) {
        this.roomName = roomName;
        this.player1 = player1;
        this.player2 = player2;
        this.game1 = game1;
        this.game2 = game2;
    }

    public String getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Game getGame1() {
        return game1;
    }

    public void setGame1(Game game1) {
        this.game1 = game1;
    }

    public Game getGame2() {
        return game2;
    }

    public void setGame2(Game game2) {
        this.game2 = game2;
    }
}
