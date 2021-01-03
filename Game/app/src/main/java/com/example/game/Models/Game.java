package com.example.game.Models;

import java.util.List;

public class Game {
    public enum State {PLAYING, WAITING}

    private String word;
    private List<String> usedLetters;
    private State state;

    public Game() {
    }

    public Game(State state) {
        this.state = state;
    }

    public Game(String word, List<String> usedLetters, State state) {
        this.word = word;
        this.usedLetters = usedLetters;
        this.state = state;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    public List<String> getUsedLetters() {
        return usedLetters;
    }

    public void setUsedLetters(List<String> usedLetters) {
        this.usedLetters = usedLetters;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
