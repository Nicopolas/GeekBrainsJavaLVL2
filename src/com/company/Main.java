package com.company;

/*
1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4,
при подаче массива другого размера необходимо бросить исключение MyArraySizeException.

2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ
или текст вместо числа), должно быть брошено исключение MyArrayDataException,
с детализацией в какой именно ячейке лежат неверные данные.

3. В методе main() вызвать полученный метод, обработать возможные исключения
MySizeArrayException и MyArrayDataException, и вывести результат расчета.
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(arrToIntAndSum(new String[][]{new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"}}));
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(arrToIntAndSum(new String[][]{new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"}}));
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(arrToIntAndSum(new String[][]{new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"},
                    new String[]{"19", "23", "29", "31"},
                    new String[]{"фигняКакаяТо", "23", "29", "31"}}));
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static int arrToIntAndSum(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr.length != 4 || arr[0].length != 4) {
            throw new MyArraySizeException();
        }
        int sum = 0;
        int i = 0;
        int j = 0;
        try {
            while (i < arr.length) {
                j = 0;
                while (j < arr.length) {
                    sum = sum + Integer.valueOf(arr[i][j]);
                    j++;
                }
                i++;
            }
        } catch (Exception e) {
            throw new MyArrayDataException(i, j);
        }
        return sum;
    }
}
