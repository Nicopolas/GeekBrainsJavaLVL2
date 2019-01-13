package com.company;

/**
 * Created by Admin on 13.01.2019.
 */
public class Team {
    String teamName;
    Participant[] participants;

    public Team(String teamName, Participant firstParticipant, Participant secondParticipant,
                Participant thirdParticipant, Participant fourthParticipant) {
        this.teamName = teamName;
        participants = new Participant[]{firstParticipant, secondParticipant, thirdParticipant, fourthParticipant};
    }

    public void showResults() {
        for (Participant participant : participants) {
            System.out.println("Член команды \"" + teamName + "\" " + participant.getName() +
                    (participant.isSuccessfulPassageCourse() ? " успешно прошел полосу препятствий" : " провалил прохождение полосы препятствий"));
        }
    }

    public void showPast() {
        System.out.println("Список успешно прошедших испытания в коменде \"" + teamName + "\"");
        for (Participant participant : participants) {
            if (!participant.isSuccessfulPassageCourse()) {
                continue;
            }
            System.out.println(participant.getName());
        }
    }
}
