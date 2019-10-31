/*
 * File:    ShapeDraw.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 18:51:16
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui;

import java.awt.Graphics;

/**
 * Интерфейс для отрисовки фигур
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public interface ShapeDraw {
    
    /**
     * Метод отрисовки фигуры
     * @param g ссылка на графический контекст
     */
    void draw(Graphics g);
    
    
    // for JDK8+
    /**
     * Метод подготовки к рисованию фигуры (вызывается перед отрсовкой фигуры)
     * @param g графический контекст
     */
    default void preDraw(Graphics g) {
        // чтобы не реализовывать во всех наследниках
//        System.out.println("@@@@ preDraw() - JDK8+");
        printMessage("preDraw");
    }

    /**
     * Метод завершения рисования фигуры (вызывается после отрисоки фигуры)
     * @param g графический контекст
     */
    default void postDraw(Graphics g) {
        // чтобы не реализовывать во всех наследниках
//        System.out.println("@@@@ preDraw() - JDK8+");
        printMessage("postDraw");
    }

    /**
     * Получить порядок отрисовки фигуры на экране
     *
     * @return значение порядка
     */
    default int getOrder() {
        return 0;
    }

    /**
     * Вычисление значения в новом диапазоне значений
     * @param value значение
     * @param inMin минимальное значение входного значения
     * @param inMax минимальное значение входного значения
     * @param outMin максимальное значение выходного значения
     * @param outMax максимальное значение выходного значения
     * @return преобразованное значение
     */
    static int map(int value, int inMin, int inMax, int outMin, int outMax) {
        double scale = (double)(outMax - outMin) / (inMax - inMin);
        return (int) ((value - inMin) * scale + outMin);
    }
    
    // for JDK9+
    /**
     * Вспомагательный метод для вывода сообщения на экран
     * @param methodName имя метода
     */
    private void printMessage(String methodName) {
        System.out.println("@@@@ ShapeDraw." + methodName +"() - JDK9+");
    }
    
}
