/*
 * File:    ShapeParameter.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 18:53:26
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui;

import java.awt.Color;

/**
 * Интерфейс для получения параметров фигур
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public interface ShapeParameter {
    
    // Константы для значений возвращаемых методом getType()
    
    /**
     * Константа названия фигуры: Неизвестно
     */
    String TYPE_UNKNOWN = "Неизвестно";
    /**
     * Константа названия фигуры: Линия
     */
    String TYPE_LINE = "Линия";
    /**
     * Константа названия фигуры: Прямоугольник
     */
    String TYPE_RECTANGLE = "Прямоугольник";
    /**
     * Константа названия фигуры: Квадрат
     */
    String TYPE_SQUARE = "Квадрат";
    /**
     * Константа названия фигуры: Овал
     */
    String TYPE_OVAL = "Овал";
    /**
     * Константа названия фигуры: Круг
     */
    String TYPE_CIRCLE = "Круг";
    /**
     * Константа названия фигуры: Текст
     */
    String TYPE_TEXT = "Текст";
    
    /**
     * Метод получения типа фигуры
     * @return название фигуры {@code String} (значения констант TYPE_*)
     */
    String getType();
    
    /**
     * Перечисление для типов фигуры возвращаемых методом getShapeType()
     */
    enum ShapeType {
        /** Неизвестно */
        Unknown, 
        /** Линия */
        Line, 
        /** Прямоугольник */
        Rectangle, 
        /** Квадрат */
        Square, 
        /** Овал */
        Oval, 
        /** Круг */
        Circle, 
        /** Текст */
        Text}
    
    /**
     * Метод получения типа фигуры (перечисление {@code ShapeType})
     * @return тип фигуры {@code ShapeType}
     * @see ShapeType
     */
    ShapeType getShapeType();
    
    /**
     * Проверка что фигура закрашена (залита краской)
     * @return {@code true} если фигура закрашенная
     */
    boolean isFill();
    
    /**
     * Получить цвет фигуры
     * @return цвет фигуры
     * @see java.awt.Color
     */
    Color getColor();
    
    /**
     * Получить периметр фигуры (длину линии)
     * @return периметр фигуры
     */
    double getPerimeter();
    
    /**
     * Получить площадь фигуры
     * @return площадь фигуры
     */
    double getSquare();
}
