/*
 * File:    Circle.java
 * Project: HelloJavaSE
 * Date:    27 нояб. 2018 г. 23:58:25
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс реализирующий фигуру Круг
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
public class Circle extends Oval {

    // ************* Constructors **************

    /**
     * Конструктор круга по умолчанию
     */
    public Circle() {
    }

    /**
     * Конструктор круга
     * @param color цвет круга
     * @param x абцисса центра круга
     * @param y ордината центра круга
     * @param r радиус круга
     */
    public Circle(Color color, int x, int y, int r) {
        super(color, x, y, r + r, r + r );
    }

    // ************* Getters & Setters **************

    // Защищаем пропорции круга: ширина и высота равны
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    // Защищаем пропорции круга: ширина и высота равны
    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }

    /**
     * Получить радиус круга
     * @return радиус круга
     */
    public int getRadius() {
        return width / 2;
    }
    
    /**
     * Установить новое значение радиуса круга
     * @param r новый радиус круга
     */
    public  void setRadius(int r) {
        //this.setWidth(2 * r);
        setWidth(r << 1); 
    }
}
