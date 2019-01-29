package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*1. Написать консольный вариант клиент\серверного приложения,
в котором пользователь может писать сообщения, как на клиентской стороне,
так и на серверной.
Т.е. если на клиентской стороне написать "Привет",
нажать Enter то сообщение должно передаться на сервер и там отпечататься в консоли.
Если сделать то же самое на серверной стороне, сообщение соответственно передается клиенту
и печатается у него в консоли.
Есть одна особенность, которую нужно учитывать:
клиент или сервер может написать несколько сообщений подряд,
такую ситуацию необходимо корректно обработать

Разобраться с кодом с занятия, он является фундаментом проекта-чата

ВАЖНО! Сервер общается только с одним клиентом,
т.е. не нужно запускать цикл, который будет ожидать второго/третьего/n-го клиентов
*/

public class Server {
    private static ServerSocket server;
    private static Socket clentSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static boolean clientOnline = false;
    private static boolean serverOnline = false;

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004);
                System.out.println("Сервер запущен");
                System.out.println("Жду соединение");
                clentSocket = server.accept();
                clientOnline = true;
                System.out.println("Есть соединение");
                try {
                    while (clientOnline) {
                        in = new BufferedReader(new InputStreamReader(clentSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clentSocket.getOutputStream()));

                        String word = in.readLine();
                        System.out.println(word);

                        out.write("Привет это сервер! Потвержаю что получил информацию. И ты написал :" + word + "\n");
                        out.flush();

                        if (word.equals("out")) {
                            clientOnline = false;
                            break;
                        }
                    }
                } finally {
                    in.close();
                    out.close();
                }
                System.out.println("while in");
            } finally {
                clentSocket.close();
                in.close();
                out.close();
                serverOnline = false;
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
