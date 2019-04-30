package com.company;

public class Main {

    public static void main(String[] args) {
        Course c = new Course(new Obstacle[]{new Obstacle(10), new Obstacle(20), new Obstacle(30), new Obstacle(50)}); // Создаем полосу препятствий
        Team team = new Team("Березка",
                new Participant("Иван", 60),
                new Participant("Володя", 65),
                new Participant("Слава", 45),
                new Participant("Никита", 55));  // Создаем команду
        c.doIt(team);               // Просим команду пройти полосу
        team.showResults();         // Показываем результаты
        team.showPast();            // Показываем прошедших дистанцию
    }
}
