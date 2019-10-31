/*
 * File:    HelloMatrix.java
 * Project: HelloJavaSE
 * Date:    Jan 28, 2019 8:20:32 AM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.util.Random;

/**
 * Работа с матрицами (многомерными массивамы) в языке Java
 * 
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloMatrix {

    /**
     * Максимальное значение генерируемых случайных чисел в матрице
     */
    private static final int MAX_BOUND = 100;
    
    /**
     * Генератор случайных чисел
     */
    private final Random r = new Random();
    
    /**
     * Главный метод приложения
     * @param args аргументы команндной строки
     */
    public static void main(String[] args) {
        HelloMatrix app = new HelloMatrix();

        // matrix 1
        int[][] matrix1 = {
            {1, 2},
            {3, 4, 5, 6},
            {7, 8, 9},
            {10}
        };
        app.printMatrix("matrix1", matrix1);
        
        // matrix 2
        int[][] matrix2 = new int[5][3];
        app.printMatrix("matrix2", matrix2);
        app.fillRandomMatrix(matrix2);
        app.printMatrix("matrix2", matrix2);
        
        // generate matrix
        int[][] matrix5 = app.generateMatrix(3, 5);
        int[][] matrix6 = app.generateDiagonalMatrix(5);
        int[][] matrix7 = app.generateOrtoDiagonalMatrix(5);
        
        app.printMatrix("matrix5", matrix5);
        app.printMatrix("matrix6", matrix6);
        app.printMatrix("matrix7", matrix7);
    }
    
    /**
     * Генератор прямоугольной матрицы
     * @param n ширина матрицы
     * @param m высота матрицы
     * @return сгенерированая матрица
     */
    public int[][] generateMatrix(int n, int m) {
        int[][] matrix = new int[n][m];
        for (int[] array : matrix) {
            for (int j = 0; j < array.length; j++) {
                array[j] = r.nextInt(MAX_BOUND);
            }
        }
        return matrix;
    }

    /**
     * Генератор диагональной матрицы
     * @param n размер матрицы
     * @return сгенерированая матрица
     */
    public int[][] generateDiagonalMatrix(int n) {
        int[][] matrix = new int[n][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new int[i + 1];
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = r.nextInt(MAX_BOUND);
            }
        }
        return matrix;
    }

    /**
     * Генератор ортодиагональной матрицы
     * @param n размер матрицы
     * @return сгенерированая матрица
     */
    public int[][] generateOrtoDiagonalMatrix(int n) {
        int[][] matrix = new int[n][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new int[n - i];
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = r.nextInt(MAX_BOUND);
            }
        }
        return matrix;
    }

    /**
     * Печать матрицы на экране
     * @param name имя переменной
     * @param matrix ссылка на матрицу
     */
    public void printMatrix(String name, int[][] matrix) {
        System.out.println(name + " = " + matrixToString(matrix));
    }

    /**
     * Приведение матрицы к строке
     * @param matrix ссылка на матрицу
     * @return строковое представление матрицы
     */
    public String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < matrix.length; i++) {
            sb.append(i > 0 ? ", [" : "  [");
            for (int j = 0; j < matrix[i].length; j++) {
                if (j > 0) sb.append(", ");
                sb.append(matrix[i][j]);
            }
            sb.append("]\n");
        }
        return sb.append(']').toString();
    }

    /**
     * Заполнить матрицу случайными числами
     * @param matrix ссылка на матрицу
     */
    public void fillRandomMatrix(int[][] matrix) {
        for (int[] array : matrix) {
            for (int i = 0; i < array.length; i++) {
                array[i] = r.nextInt(MAX_BOUND);
            }
        }
    }
}
