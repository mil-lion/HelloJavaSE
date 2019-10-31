/*
 * File:    FillSquare.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 23:37:54
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import java.awt.Graphics;
import ru.lionsoft.javase.hello.gui.Filled;

/**
 * Класс реализирующий фигуру Закарашенный Квадрат
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@Filled
public final class FillSquare extends Square {

    // ************* Constructors **************

    /**
     * Конструктор закрашенного квадрата по умолчанию
     */
    public FillSquare() {
    }

    /**
     * Конструктор закрашенного квадрата
     * @param color цвет квадрата
     * @param x абцисса левого верхнего угла квадрата
     * @param y ордината левого верхнего угла квадрата
     * @param size размер квадрата
     */
    public FillSquare(Color color, int x, int y, int size) {
        super(color, x, y, size);
    }

    // ************* Implements Methods Of Interface ShapeDraw **************
    
    // Реализуем метод рисования залитого квадрата
    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }

    // ************* Implements Methods of Interface ShapeParameter **************
    
    // Фигура закрашенная!
    @Override
    public boolean isFill() {
        return true;
    }
}
