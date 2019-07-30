package com.example.sufyanlatif.myapplication.models;

import java.io.Serializable;

public class Score {
    public String id, correct, incorrect, game_id, child_id;

    public Score(String id, String correct, String incorrect, String game_id){
        this.id=id;
        this.correct= correct;
        this.incorrect= incorrect;
        this.game_id= game_id;
    }
}
