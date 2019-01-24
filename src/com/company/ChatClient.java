package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
1. Создать окно для клиентской части чата: большое текстовое поле для отображения
переписки в центре окна.
Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений
на нижней панели.
Сообщение должно отсылаться либо по нажатию кнопки на форме,
либо по нажатию кнопки Enter.
При «отсылке» сообщение перекидывается из нижнего поля в центральное.
 */

public class ChatClient extends JFrame {
    private JTextField textMessageField;
    private JTextArea correspondenceDisplayField;
    private JButton sendMessageBtm;
    private String textFieldMessageStr = "Введите ваше сообщение: ";

    public ChatClient() {
        setSize(600, 500);
        setTitle("Клиентская часть чата");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Поле для отображения переписки
        correspondenceDisplayField = new JTextArea();
        correspondenceDisplayField.setToolTipText("Большое текстовое поле для отображения\n" +
                "переписки в центре окна");
        correspondenceDisplayField.setEditable(false);
        correspondenceDisplayField.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(correspondenceDisplayField);
        add(jsp, BorderLayout.CENTER);

        //Поле ввода сообщения
        textMessageField = new JTextField(textFieldMessageStr);
        textMessageField.setForeground(Color.GRAY);
        textMessageField.setToolTipText("Однострочное текстовое поле для ввода сообщений");

        //Кнопка отправить
        sendMessageBtm = new JButton("Отправить");

        //Добавляем панель внизу окна
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(sendMessageBtm, BorderLayout.EAST);
        bottomPanel.add(textMessageField, BorderLayout.CENTER);

        // вешаем слушателя на кнопку "Оправить"
        sendMessageBtm.addActionListener(e ->
                sendMessage()
        );

        //оправка сообщения по нажатию Enter
        textMessageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // добавляем placeholder для поля текстового поля
        // пришлось писать самому если в JTextField они и есть то я не нашел
        textMessageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textMessageField.getText().equals(textFieldMessageStr)) {
                    textMessageField.setText("");
                    textMessageField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textMessageField.getText().isEmpty()) {
                    textMessageField.setForeground(Color.GRAY);
                    textMessageField.setText(textFieldMessageStr);
                }
            }
        });

        setVisible(true);
    }

    //метод отправки сообщения
    public void sendMessage() {
        if (!(textMessageField.getText().trim().isEmpty() || textMessageField.getText().equals(textFieldMessageStr))) {
            correspondenceDisplayField.append(textMessageField.getText() + "\n");
            textMessageField.setText("");
            textMessageField.grabFocus();
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
