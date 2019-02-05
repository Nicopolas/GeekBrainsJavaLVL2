package com.company.client;

import java.io.*;
import java.net.Socket;

/*
 *Реализовать личные сообщения, если клиент пишет «/w nick3 Привет»,
 * то только клиенту с ником nick3 должно прийти сообщение «Привет».
 */

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader; //console!
    private static BufferedReader in;
    private static BufferedWriter out ;
    private static boolean clientOnline = false;
    private static boolean serverOnline = false;
    private static String name;

    public Client(String name) {
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

    private static void stopClient() {
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

    public static void onAuth() {
        try {
            out.write("/auth " + name);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            stopClient();
        }
    }
}
