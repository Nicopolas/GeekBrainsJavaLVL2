package com.company.client;

import java.io.*;
import java.net.Socket;

public class Client1 {
    private static Socket clientSocket;
    private static BufferedReader reader; //console!
    private static BufferedReader in;
    private static BufferedWriter out;
    private static boolean clientOnline = false;
    private static boolean serverOnline = false;
    private static String name;
    private static String AUTH_ERROR_MSG = "Ошибка аутентификации неверный формат запроса!";
    private static String AUTH_SUCCESSFUL = "/authOn";

    public static void main(String[] args) {
        new Client1("Sereja");
    }


    public Client1(String name) {
        this.name = name;
        startClient();
    }

    private void startClient() {
        try {
            clientSocket = new Socket("localhost", 4004);
            reader = new BufferedReader(new InputStreamReader(System.in));
            out = new BufferedWriter((new OutputStreamWriter(clientSocket.getOutputStream())));
            onAuth();
            clientOnline = true;
            new Thread(() -> startConsoleListener()).start();
            new Thread(() -> startServerListener()).start();
        } catch (Exception e) {
            stopClient();
        }
    }

    private void startServerListener() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String serverMsg = in.readLine();
            if (!serverMsg.contains(AUTH_SUCCESSFUL)) {
                System.out.println(AUTH_ERROR_MSG);
                stopClient();
            }
            while (clientOnline) {
                serverMsg = in.readLine();
                if (serverMsg.equals("/end")) {
                    System.out.println("Сервер отсоединился");
                    serverOnline = false;
                    stopClient();
                    break;
                }
                System.out.println("Сообщение от Server: " + serverMsg);
            }
        } catch (Exception e) {
            stopClient();
        }
        stopClient();
    }

    private void startConsoleListener() {
        try {
            System.out.println("Есть подключение к серверу");
            while (clientOnline) {
                String word = reader.readLine();
                out.write(word + "\n");
                out.flush();

                if (word.equals("/end")) {
                    stopClient();
                    break;
                }
            }
        } catch (Exception e) {
            stopClient();
        }
    }

    private void stopClient() {
        if (!clientOnline) {
            return;
        }
        try {
            in.close();
            out.close();
            clientSocket.close();
            clientOnline = false;
            System.out.println("Клиент закрыт...");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void onAuth() {
        try {
            out.write("/auth " + name + "\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            stopClient();
        }
    }
}
