/*
 * File:    FillRectangle.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 23:37:54
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import java.awt.Graphics;
import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlRootElement;
import ru.lionsoft.javase.hello.gui.Filled;
import ru.lionsoft.javase.hello.gui.PostDraw;

/**
 * Класс реализирующий фигуру Закарашенный Прямоугольник
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
@Filled(name = "ПРЯМОУГОЛЬНИК")
public final class FillRectangle extends Rectangle {

    // ************* Constructors **************

    /**
     * Конструктор закрашенного прямоугольника по умолчанию
     */
    public FillRectangle() {
    }

    /**
     * Конструктор закрашенного прямоугольника
     * @param color цвет прямоугольника
     * @param x абцисса левого верхнего угла прямоугольника
     * @param y ордината левого верхнего угла прямоугольника
     * @param width ширина прямоугольника
     * @param height высота прямоугольника
     */
    public FillRectangle(Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height);
    }
        
    // ************* Implements Methods Of Interface ShapeDraw **************
    
    // Реализуем метод рисования залитого прямоугольника
    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }

    // ************* Implements Methods of Interface ShapeParameter **************
    
    // Фигура закрашенная!
    @JsonbTransient
    @Override
    public boolean isFill() {
        return true;
    }
    
    // ************* Callback Methods **************

    // Аннотированный метод который будет вызываться после отрисовки фигуры
    @PostDraw
    public void afterDraw2(Graphics g) {
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".afterDraw2()!!!");
    }
}
