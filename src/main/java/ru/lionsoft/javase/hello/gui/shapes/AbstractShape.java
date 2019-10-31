/*
 * File:    AbstractShape.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 18:50:13
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import java.awt.Graphics;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.lionsoft.javase.hello.gui.ShapeDraw;
import ru.lionsoft.javase.hello.json.ColorJsonAdapter;
import ru.lionsoft.javase.hello.xml.jaxb.ColorXmlAdapter;

/**
 * Абстрактный класс с общими свойствами всех фигур
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public abstract class AbstractShape implements ShapeDraw {

    // ************* Constructors **************

    /**
     * Конструктор фигуры по умолчанию
     */
    public AbstractShape() {
    }

    /**
     * Конструктор-помощник для наследников
     * @param color цвет фигуры
     * @param x абциасса точки рисования фигуры
     * @param y ордината точки рисования фигуры
     */
    public AbstractShape(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    // ************* Properties **************
    
    /**
     * Цвет фигуры
     */
    protected Color color;

    /**
     * Get the value of color
     *
     * @return the value of color
     */
//    @XmlAttribute
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    @JsonbTypeAdapter(ColorJsonAdapter.class)
    public Color getColor() {
        return color;
    }

    /**
     * Set the value of color
     *
     * @param color new value of color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    
    /**
     * Абцисса точки рисования фигуры
     */
    protected int x;

    /**
     * Get the value of x
     *
     * @return the value of x
     */
//    @XmlAttribute
    public int getX() {
        return x;
    }

    /**
     * Set the value of x
     *
     * @param x new value of x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Ордината точки рисования фигуры
     */
    protected int y;

    /**
     * Get the value of y
     *
     * @return the value of y
     */
//    @XmlAttribute
    public int getY() {
        return y;
    }

    /**
     * Set the value of y
     *
     * @param y new value of y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Порядок отрисовки фигур
     */
    protected int order = 1;

    /**
     * Get the value of order
     *
     * @return the value of order
     */
    @XmlAttribute
    @Override
    public int getOrder() {
        return order;
    }

    /**
     * Set the value of order
     *
     * @param order new value of order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    
    // ************* Methods **************

    // ************* Override Methods Of Interface ShapeDraw **************

    /**
     * Старое значение цвета
     */
    private Color oldColor;

    // Метод подготовки фигуры к рисованию для сохранения старого цвета кисти
    // и установки цвета кисти для фигуры
    @Override
    public void preDraw(Graphics g) {
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".preDraw()");
        // Сохраняем текущий цвет кисти
        oldColor = g.getColor();
        // Утсанавливаем новый цвет кисти для рисования фигуры
        g.setColor(color);
    }
    
    // Метод вызывается после отрисовки фигуры для возврата цвета кисти
    @Override
    public void postDraw(Graphics g) {
        System.out.println("@@@@ call " + getClass().getSimpleName() + ".postDraw()");
        // Восстанавливаем цвет кисти
        g.setColor(oldColor);
    }
    
    // ************* toString **************

    // Периписываем приведение к строке, метод-помощник для потомков (наследников)
    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' + "color=" + color + ", x=" + x + ", y=" + y;
    }  
}
