/*
 * File:    HelloArray.java
 * Project: HelloJavaSE
 * Date:    Jan 28, 2019 8:19:56 AM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloArray {
    
    /**
     * Максимальное значение генерируемого случайного числа 
     */
    private static final int MAX_BOUND = 100;
    
    /**
     * Генератор псевдослучайных чисел
     */
    private final Random r = new Random();
    
    /**
     * Основной метод приложения
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        HelloArray app = new HelloArray();
        
        int array1[] = {1, 2, 3, 4, 5};
        app.printArray("array1", array1);
        
        int[] array2 = new int[10];
        app.printArray("array2", array2);
        app.fillArray(array2);
        app.printArray("array2", array2);
        
        int[] array3 = app.generateArray(7);
        app.printArray("array3", array3);
        
        // Использования класса помощника Arrays
        System.out.println("array3 = " + Arrays.toString(array3));
        
        // Сортировка
        Arrays.sort(array3);
        System.out.println("array3(asc) = " + Arrays.toString(array3));

        // Массив объектов
        Box[] boxArray = {
            new Box(Box.TyepSize.Large), new Box(Box.TyepSize.Small), 
            new Box(Box.TyepSize.Medium), new Box(10)
        };
        System.out.println("boxArray = " + Arrays.toString(boxArray));

        // Сортировка
        Arrays.sort(boxArray);
        System.out.println("boxArray(asc) = " + Arrays.toString(boxArray));
        
        // Сортировка по убыванию с использование класса сравнения коробок
        Arrays.sort(boxArray, new BoxComparator(false));
        System.out.println("boxArray(desc) = " + Arrays.toString(boxArray));

        // Сортировка по возрастанию и NULL в конце списка
        boxArray[2] = null;
        Arrays.sort(boxArray, new BoxComparator(true, false));
        System.out.println("boxArray(asc, nulls last) = " + Arrays.toString(boxArray));
        
        // Сортировка по убыванию c использованием анонимного класса
        Arrays.sort(boxArray, new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                if (o1 == o2) return 0;
                if (o1 == null) return -1;
                return -o1.compareTo(o2);
            }
        });
        System.out.println("boxArray(desc, anonymous) = " + Arrays.toString(boxArray));

        // Сортировка по возрастанию c использованием лямбда
        boxArray[0] = new Box(10);
        Arrays.sort(boxArray, (Box box1, Box box2) -> box1.compareTo(box2)); // way 1
        Arrays.sort(boxArray, (b1, b2) -> b1.compareTo(b2)); // way 2
        Arrays.sort(boxArray, Box::compare); // way 3
        System.out.println("boxArray(asc, lambda) = " + Arrays.toString(boxArray));
    }

    /**
     * Распечатать на экране переменную типа целочисленный массив
     * @param name имя переменной
     * @param array ссылка на массив
     */
    public void printArray(String name, int[] array) {
        System.out.println(name + " = " + arrayToString(array));
    }

    /**
     * Приведение массива к строке
     * @param array ссвлка на целочисленный массив
     * @return строковое представление массива
     */
    public String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(array[i]);
        }
        return sb.append(']').toString();
    }

    /**
     * Заполнить массив случайными числами
     * @param array ссылка на целочисленный массив
     */
    public void fillArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(MAX_BOUND);
        }
    }
    
    /**
     * Сгенерировать целочисленный массив
     * @param n размерность массива
     * @return целочисленный массив
     */
    public int[] generateArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(MAX_BOUND);
        }
        return array;
    }
}
