/*
 * File:    Square.java
 * Project: HelloJavaSE
 * Date:    28 нояб. 2018 г. 0:32:51
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;

/**
 * Класс реализирующий фигуру Квадрад
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class Square extends Rectangle {
    
    // ************* Constructors **************

    /**
     * Конструктор квадрата по умолчанию
     */
    public Square() {
    }

    /**
     * Конструктор квадрата
     * @param color цвет квадрата
     * @param x абцисса левого верхнего угла квадрата
     * @param y ордината левого верхнего угла квадрата
     * @param size размер квадрата
     */
    public Square(Color color, int x, int y, int size) {
        super(color, x, y, size, size);
    }
    
    // ************* Getters & Setters **************

    // Защищаем пропорции квадрата: ширина и высота равны
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    // Защищаем пропорции квадрата: ширина и высота равны
    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
