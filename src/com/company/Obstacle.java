package com.company;

/**
 * Created by Admin on 13.01.2019.
 */
public class Obstacle {
    int difficulty;

    public Obstacle(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isPassed(Participant participant) {
        return difficulty <= participant.getMaxDifficulty();
    }
}
