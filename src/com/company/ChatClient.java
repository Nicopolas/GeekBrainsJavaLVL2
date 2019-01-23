package com.company;

import javax.swing.*;
import java.awt.*;
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
    private JToggleButton sendBtn;
    private JTextField smallField, bigField;

    public ChatClient() throws HeadlessException {
        super("Клиентская часть чата");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bigField = new JTextField(50);
        bigField.setToolTipText("большое текстовое поле для отображения\n" +
                "переписки в центре окна");

        smallField = new JTextField(15);
        smallField.setToolTipText("Однострочное текстовое поле для ввода сообщений");

        sendBtn =  new JToggleButton("Отправить", false);

        // Размещение эллементов в окне
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(bigField));
        contents.add(smallField);
        contents.add(sendBtn);

        // Вывод окна на экран
        setContentPane(contents);
        setSize(500, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
