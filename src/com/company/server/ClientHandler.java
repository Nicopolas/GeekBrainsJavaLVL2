package com.company.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
    private static int clientsCount = 0;
    private static String KEY_OF_SESSION_END = "session end";

    private static String NEW_CLIENTS_MSG = "Новый участник! Теперь нас = ";
    private static String EXIT_CLIENT_MSG = "Участник вышел! Теперь нас = ";

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

            server.sendMsgToAllClients(NEW_CLIENTS_MSG + clientsCount);

            while (true) {
                if (inMessage.hasNext()) {
                    String clientsMsg = inMessage.nextLine();
                    System.out.println(clientsMsg);

                    if (clientsMsg.equalsIgnoreCase(KEY_OF_SESSION_END)) {
                        break;
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

    public void sendMessage(String msgText) {
        outMessage.println(msgText);
        outMessage.flush();
    }

    public void exitClientSession() {
        server.removeClient(this);
        clientsCount--;
        server.sendMsgToAllClients(EXIT_CLIENT_MSG + clientsCount);
    }


}
