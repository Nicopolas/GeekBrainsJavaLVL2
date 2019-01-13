package com.company;

/**
 * Created by Admin on 13.01.2019.
 */
public class Participant {
    String name;
    int maxDifficulty;
    boolean successfulPassageCourse;

    public Participant(String name, int maxDifficulty) {
        this.name = name;
        this.maxDifficulty = maxDifficulty;
    }

    public String getName() {
        return name;
    }

    public int getMaxDifficulty() {
        return maxDifficulty;
    }

    public boolean isSuccessfulPassageCourse() {
        return successfulPassageCourse;
    }

    public void setSuccessfulPassageCourse(boolean successfulPassageCourse) {
        this.successfulPassageCourse = successfulPassageCourse;
    }
}
