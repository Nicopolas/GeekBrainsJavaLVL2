package com.company.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ilnaz-92@yandex.ru
 * Created on 01/02/2019
 */

/*
 *Реализовать личные сообщения, если клиент пишет «/w nick3 Привет»,
 * то только клиенту с ником nick3 должно прийти сообщение «Привет».
 */

public class Server {
    static final int PORT = 4004;
    private List<ClientHandler> clients = new ArrayList<>();

    public Server() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler newClient = new ClientHandler(clientSocket, this);
                clients.add(newClient);

                new Thread(newClient).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMsgToAllClients(String msgText) {
        System.out.println(msgText);
        clients.forEach(clientHandler -> clientHandler.sendMessage(msgText));
    }

    public boolean sendMsgTo(String to, String message) {
        return sendMsgTo("", to, message);
    }

    public boolean sendPrivateMsgTo(String from, String message) {
        String msgName = message.split(" ")[1];
        String msgText = message.split(msgName)[1];
        return sendMsgTo(from, msgName, msgText);
    }

    public boolean sendMsgTo(String from, String to, String message) {
        for (ClientHandler client : clients) {
            if (client.getName().equals(to)) {
                client.sendMessage(from, message);
                return true;
            }
        }
        return false;
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

}
