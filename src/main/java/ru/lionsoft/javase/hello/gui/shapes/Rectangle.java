/*
 * File:    Rectangle.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 23:26:17
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import java.awt.Graphics;
import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlRootElement;
import ru.lionsoft.javase.hello.gui.PostDraw;
import ru.lionsoft.javase.hello.gui.PreDraw;
import ru.lionsoft.javase.hello.gui.ShapeParameter;

/**
 * Класс реализирующий фигуру Прямоугольник
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
public class Rectangle extends AbstractShape implements ShapeParameter {
    
    // ************* Constructors **************

    /**
     * Конструктор прямоугольника по умолчанию
     */
    public Rectangle() {
    }

    /**
     * Конструктор прямоугольника
     * @param color цвет прямоугольника
     * @param x абцисса левого верхнего угла прямоугольника
     * @param y ордината левого верхнего угла прямоугольника
     * @param width ширина прямоугольника
     * @param height высота прямоугольника
     */
    public Rectangle(Color color, int x, int y, int width, int height) {
        super(color, x, y);
        this.width = width;
        this.height = height;
    }
    
    // ************* Properties **************

    /**
     * Ширина прямоугольника
     */
    protected int width;

    /**
     * Get the value of width
     *
     * @return the value of width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the value of width
     *
     * @param width new value of width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Высота прямоугольника
     */
    protected int height;

    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the value of height
     *
     * @param height new value of height
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    // ************* Implements Of Interface ShapeDraw **************
    
    // Реализуем метод рисования прямоугольника
    @Override
    public void draw(Graphics g) {
        g.drawRect(x, y, width, height);
    }

    // ************* toString **************

    // Переписываем метод приведения фигуры к строке (используем метод-помощник родителя)
    @Override
    public String toString() {
        return super.toString() + ", width=" + width + ", height=" + height + '}';
    }
       
    // ************* Implements Methods of Interface ShapeParameter **************

    // Тип фигуры: Прямоугольник или Квадрат (если ширина и высота равны)
    @JsonbTransient
    @Override
    public String getType() {
        return width == height ? TYPE_SQUARE : TYPE_RECTANGLE;
    }

    // Тип фигуры: Прямоугольник или Квадрат (если ширина и высота равны)
    @JsonbTransient
    @Override
    public ShapeType getShapeType() {
        return width == height ? ShapeType.SQUARE : ShapeType.RECTANGLE;
    }

    // По умолчанию: фигура не закрашенная!
    @JsonbTransient
    @Override
    public boolean isFill() {
        return false;
    }

    // Периметр прямоугольника
    @JsonbTransient
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    // Площадь прямоугольника
    @JsonbTransient
    @Override
    public double getSquare() {
        return width * height;
    }

    // ************* Callback Methods **************

    // Аннотированный метод, который будет вызываться перед отрисовкой фигуры
    @PreDraw
    public void beforeDraw1(Graphics g) {
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".beforeDraw1()!!!");
    }

    // Аннотированный метод, который будет вызываться перед отрисовкой фигуры
    @PreDraw
    public void beforeDraw2(Graphics g) {
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".beforeDraw2()!!!");
    }

    // Аннотированный метод, который будет вызываться после отрисовкой фигуры
    @PostDraw
    public void afterDraw(Graphics g) {
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".afterDraw()!!!");
    }
}
