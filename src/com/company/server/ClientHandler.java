package com.company.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * @author ilnaz-92@yandex.ru
 * Created on 01/02/2019
 */
public class ClientHandler implements Runnable {
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static final int PORT = 4004;
    private static final String HOST = "localhost";
    private Socket clientSocket;
    private Server server;
    private String name;
    private static int clientsCount = 0;
    private static String KEY_OF_SESSION_END = "session end";
    private static String NEW_CLIENTS_MSG = "Новый участник %s! Теперь нас = %s";
    private static String EXIT_CLIENT_MSG = "Участник вышел! Теперь нас = ";
    private static String AUTH_ERROR_MSG = "Ошибка аутентификации неверный формат запроса!";
    private static String SEND_MSG_ERROR = "Ошибка отправки сообщения не найден пользователь!";
    private static String AUTH_REGEXP = "/auth(\\s)(\\w+)";
    private static String PRIVATE_MSG_REGEXP = "^/w (\\w+)(\\s)(.+)";
    private static String AUTH_SUCCESSFUL = "/authOn";

    public ClientHandler(Socket clientSocket, Server server) {

        clientsCount++;
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());
            this.inMessage = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String clientsMsg = inMessage.nextLine();
            if (!clientsMsg.matches(AUTH_REGEXP)) {
                System.out.println(AUTH_ERROR_MSG);
                exitClientSession();
            }
            name = parseName(clientsMsg);
            server.sendMsgTo(name, AUTH_SUCCESSFUL);
            server.sendMsgToAllClients(String.format(NEW_CLIENTS_MSG, name, clientsCount));
            while (true) {
                if (inMessage.hasNext()) {
                    clientsMsg = inMessage.nextLine();
                    System.out.println(clientsMsg);

                    if (clientsMsg.equalsIgnoreCase(KEY_OF_SESSION_END)) {
                        break;
                    }

                    if (clientsMsg.matches(PRIVATE_MSG_REGEXP)) {
                        if (!server.sendPrivateMsgTo(name, clientsMsg)) {
                            server.sendMsgTo(name, SEND_MSG_ERROR);
                        }
                        continue;
                    }

                    server.sendMsgToAllClients(clientsMsg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exitClientSession();
        }
    }

    public String getName() {
        return name;
    }

    private String parseName(String authMessage) {
        return authMessage.split(" ")[1];
    }

    public void sendMessage(String msgText) {
        outMessage.println(msgText);
        outMessage.flush();
    }

    public void sendMessage(String name, String msgText) {
        outMessage.println("From: " + name + " " + msgText);
        outMessage.flush();
    }

    public void exitClientSession() {
        server.removeClient(this);
        clientsCount--;
        server.sendMsgToAllClients(EXIT_CLIENT_MSG + clientsCount);
    }
}
