/*
 * File:    Oval.java
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
import ru.lionsoft.javase.hello.gui.Shape;
import ru.lionsoft.javase.hello.gui.ShapeDrawCallback;

/**
 * Класс реализирующий фигуру Овал
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
public class Oval extends AbstractShape implements Shape, ShapeDrawCallback {
    
    // ************* Constructors **************

    /**
     * Конструктор овала по умолчанию
     */
    public Oval() {
    }

    /**
     * Конструктор овала
     * @param color цвет овала
     * @param x абцисса центра овала
     * @param y ордината центра овала
     * @param width ширина овала (горизонтальная ось овала)
     * @param height высота овала (вертикальная ось овала)
     */
    public Oval(Color color, int x, int y, int width, int height) {
        super(color, x, y);
        this.width = width;
        this.height = height;
    }
    
    // ************* Properties **************

    /**
     * Ширина овала
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
     * Высота овала
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
    
    // Реализуем метод рисования овала
    @Override
    public void draw(Graphics g) {
        g.drawOval(x - width / 2, y - height / 2, width, height);
    }

    // ************* toString **************

    // Переписываем метод приведения фигуры к строке (используем метод-помощник родителя)
    @Override
    public String toString() {
        return super.toString() + ", width=" + width + ", height=" + height + '}';
    }
       
    // ************* Implements Methods of Interface ShapeParameter **************

    // Тип фигуры: Овал или Круг (если ширина и высота овалы равны)
    @JsonbTransient
    @Override
    public String getType() {
        return width == height ? TYPE_CIRCLE : TYPE_OVAL;
    }

    // Тип фигуры: Овал или Круг (если ширина и высота овалы равны)
    @JsonbTransient
    @Override
    public ShapeType getShapeType() {
        return width == height ? ShapeType.Circle : ShapeType.Oval;
    }

    // По умолчанию: фигура не закрашенная!
    @JsonbTransient
    @Override
    public boolean isFill() {
        return false;
    }

    // Периметр овала
    @JsonbTransient
    @Override
    public double getPerimeter() {
        return Math.PI * (width + height) / 2;
    }

    // Площадь овала
    @JsonbTransient
    @Override
    public double getSquare() {
        return Math.PI * width * height / 4;
    }

    // ************* Implements Callback Methods Of Interface ShapeDrawCallback **************

    // Переопределяем метод интерфейса ShapeDrawCallback и вызываем методы родителя
    @Override
    public void preDraw(Graphics g) {
        super.preDraw(g);
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".preDraw(g)!!!!!");
    }
    
    // Переопределяем метод интерфейса ShapeDrawCallback и вызываем методы родителя
    @Override
    public void postDraw(Graphics g) {
        super.postDraw(g);
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".postDraw(g)!!!!!");
    }
}
