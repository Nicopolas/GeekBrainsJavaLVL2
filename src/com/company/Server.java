package com.company;

import java.io.*;
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
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static boolean clientOnline = false;
    private static boolean serverOnline = false;

    public static void main(String[] args) {
        try {
            startServer();
            startSessionWithClient();
            stopServer();
        } catch (Exception e) {
            stopServer();
        }
    }

    private static void startServer() throws IOException {
        server = new ServerSocket(4004);
        System.out.println("Сервер запущен");
        serverOnline = true;
        waitClient();
    }

    private static void stopServer() {
        if (!serverOnline) {
            return;
        }
        try {
            clientSocket.close();
            out.close();
            in.close();
            server.close();
            serverOnline = false;
            System.out.println("Сервер закрыт...");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void waitClient() throws IOException {
        System.out.println("Жду соединение");
        clientSocket = server.accept();
    }

    private static void startSessionWithClient() throws IOException {
        clientOnline = true;
        System.out.println("Есть соединение c Client");
        new Thread(() -> startConsoleListener()).start();
        startClientListener();
    }

    private static void startClientListener() throws IOException {
        while (clientOnline) {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String clientMsg = in.readLine();
            System.out.println("Сообщение от Client: " + clientMsg);
            if (clientMsg.equals("out")) {
                clientOnline = false;
                break;
            }
        }
        System.out.println("Client отсоединился");

    }

    private static void startConsoleListener() {
        try {
            while (clientOnline) {
                reader = new BufferedReader(new InputStreamReader(System.in));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String serverMsg = reader.readLine();
                out.write(serverMsg + "\n");
                out.flush();

                if (serverMsg.equals("out")) {
                    stopServer();
                }
            }
        } catch (Exception e) {
            stopServer();
        }
    }
}
