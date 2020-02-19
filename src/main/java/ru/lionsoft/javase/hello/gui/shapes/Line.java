/*
 * File:    Line.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 23:00:30
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import java.awt.Graphics;
import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс реализирующий фигуру Линия
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
public class Line extends AbstractShape {
    
    // ************* Constructors **************

    /**
     * Конструктор линии по умолчанию
     */
    public Line() {
    }

    /**
     * Конструктор линии
     * @param color цвет линии
     * @param x1 абцисса первой точки линии
     * @param y1 ордината первой точки линии
     * @param x2 абцисса второй точки линии
     * @param y2 ордината второй точки линии
     */
    public Line(Color color, int x1, int y1, int x2, int y2) {
        super(color, x1, y1);
        this.x2 = x2;
        this.y2 = y2;
    }
    
    // ************* Properties **************

    /**
     * Абцисса второй точки линии
     */
    protected int x2;

    /**
     * Get the value of x2
     *
     * @return the value of x2
     */
    public int getX2() {
        return x2;
    }

    /**
     * Set the value of x2
     *
     * @param x2 new value of x2
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * Ордината второй тички линии
     */
    protected int y2;

    /**
     * Get the value of y2
     *
     * @return the value of y2
     */
    public int getY2() {
        return y2;
    }

    /**
     * Set the value of y2
     *
     * @param y2 new value of y2
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }
    
    // ************* Implements Of Interface ShapeDraw **************
    
    // Реализуем метод рисования линии
    @Override
    public void draw(Graphics g) {
        g.drawLine(x, y, x2, y2);
    }

    // ************* toString **************

    // Переписываем метод приведения фигуры к строке (используем метод-помощник родителя)
    @Override
    public String toString() {
        return super.toString() + ", x2=" + x2 + ", y2=" + y2 + '}';
    }
        
    // ************* Methods **************

    /**
     * Получить длину линии
     * @return длина линии
     */
    @JsonbTransient
    public double getLineSize() {
        return Math.hypot((x2 - x), (y2 - y));
    }

}
