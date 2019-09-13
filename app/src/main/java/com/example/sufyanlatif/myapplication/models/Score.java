package com.example.sufyanlatif.myapplication.models;

import java.io.Serializable;

public class Score {

    public String id, correct, incorrect, gameId, childId;

    public Score(){}

    public Score(String id, String correct, String incorrect, String gameId){
        this.id=id;
        this.correct= correct;
        this.incorrect= incorrect;
        this.gameId= gameId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(String incorrect) {
        this.incorrect = incorrect;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }
}
