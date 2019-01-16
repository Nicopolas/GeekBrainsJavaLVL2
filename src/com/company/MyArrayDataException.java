package com.company;

public class MyArrayDataException extends Exception {
    public MyArrayDataException(int i, int j) {
        super(String.format("Данные в ячейки [%d][%d] не являются цифрами", i + 1, j + 1));
    }
}
