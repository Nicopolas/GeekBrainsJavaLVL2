package com.company;

/**
 * Created by Admin on 13.01.2019.
 */
public class Course {
    Obstacle[] obstacles;

    public Course(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        for (Participant participant : team.participants) {
            for (Obstacle obstacle : obstacles) {
                if (!obstacle.isPassed(participant)) {
                    participant.setSuccessfulPassageCourse(false);
                    break;
                }
                participant.setSuccessfulPassageCourse(true);
            }
        }
    }
}
