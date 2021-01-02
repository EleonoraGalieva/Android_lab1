package com.example.game.Models;

public class User {
    private String id;
    private String username;
    private int winnings;
    private int losses;
    private String imageURL;
    private boolean gravatar;

    public User() {
    }

    public User(String id, String username, String imageURL, boolean gravatar, int winnings, int losses) {
        this.id = id;
        this.username = username;
        this.winnings = winnings;
        this.losses = losses;
        this.imageURL = imageURL;
        this.gravatar = gravatar;
    }

    public String getId() {
        return id;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isGravatar() {
        return gravatar;
    }

    public void setGravatar(boolean gravatar) {
        this.gravatar = gravatar;
    }
}
