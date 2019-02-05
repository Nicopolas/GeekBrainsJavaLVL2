package com.company.client;

import static java.lang.Thread.sleep;

public class ClientLauncher {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Client("Vanja");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new Client("Sereja");
            }
        }).start();

    }
}
