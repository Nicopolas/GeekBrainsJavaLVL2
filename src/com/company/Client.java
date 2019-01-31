package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader; //console!
    private static BufferedReader in;
    private static BufferedWriter out;
    private static boolean clientOnline = false;
    private static boolean serverOnline = false;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())));
                clientOnline = true;
                while (clientOnline) {
                    System.out.println("Напишите то что хотели сказать!");
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();

                    String serverWord = in.readLine();
                    System.out.println("Сообщение от Server: " + serverWord);

                    if (word.equals("out")) {
                        clientOnline = false;
                        break;
                    }
                }
            } finally {
                System.out.println("Клиент закрыт...");
                clientOnline = false;
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //private static void
}
