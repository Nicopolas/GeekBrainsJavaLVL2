package com.company;

import java.io.*;
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
            clientSocket = new Socket("localhost", 4004);
            clientOnline = true;
            new Thread(() -> startConsoleListener()).start();
            startServerListener();
        } catch (Exception e) {
            stopClient();
        }
    }

    private static void startServerListener() throws IOException {
        while (clientOnline) {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String serverMsg = in.readLine();
            if (serverMsg.equals("out")) {
                System.out.println("Сервер отсоединился");
                serverOnline = false;
                stopClient();
                break;
            }
            System.out.println("Сообщение от Server: " + serverMsg);
        }
        stopClient();
    }

    private static void startConsoleListener() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            out = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())));
            System.out.println("Есть подключение к серверу");
            while (clientOnline) {
                String word = reader.readLine();
                out.write(word + "\n");
                out.flush();

                if (word.equals("out")) {
                    stopClient();
                    break;
                }
            }
        } catch (Exception e) {
            stopClient();
        }
    }

    private static void stopClient() {
        if (!clientOnline) {
            return;
        }
        try {
            System.out.println("Клиент закрыт...");
            clientOnline = false;
            clientSocket.close();
            in.close();
            out.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
