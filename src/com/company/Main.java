package com.company;

import java.util.*;
/*
1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
Посчитать сколько раз встречается каждое слово.
 */
/*
2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
В этот телефонный справочник с помощью метода add() можно добавлять записи.
С помощью метода get() искать номер телефона по фамилии.
Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
тогда при запросе такой фамилии должны выводиться все телефоны.
 */

public class Main {
    private static final String[] arr = {"ноль",
            "один",
            "два",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять",
            "десять",
            "ноль",
            "один",
            "два",
            "три",
            "четыре",
            "четыре",
    };

    public static void main(String[] args) {
        firstTask(arr);

        //Заносим данные в телефонный справочник
        Phonebook phonebook = new Phonebook();
        phonebook.add("Иванов", "+79031111111");
        phonebook.add("Петров", "+79031111112");
        phonebook.add("Васечкин", "+79031111113");
        phonebook.add("Иванов", "+79031111114");

        //Возвращаем номера телефонов по фамилии
        System.out.println("\n" + phonebook.get("Иванов"));
    }

    public static void firstTask(String[] arr) {
        //Выводим список уникальных слов
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(arr));
        System.out.println(uniqueWords + "\n");

        //Выводи список слов и количество их повторов
        Map<String, Integer> wordListAndCount = new HashMap<>();
        for (String word : arr) {
            if (wordListAndCount.get(word) != null) {
                wordListAndCount.put(word, wordListAndCount.get(word) + 1);
                continue;
            }
            wordListAndCount.put(word, 1);
        }

        for (String word : wordListAndCount.keySet()) {
            System.out.println(word + ": " + wordListAndCount.get(word));
        }
    }
}
