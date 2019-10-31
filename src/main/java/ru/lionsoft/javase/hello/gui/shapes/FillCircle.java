/*
 * File:    FillCircle.java
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

/**
 * Класс реализирующий фигуру Закарашенный Круг
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
@Filled
public final class FillCircle extends Circle {

    // ************* Constructors **************

    /**
     * Конструктор закрашенного круга по умолчанию
     */
    public FillCircle() {
    }

    /**
     * Конструктор закрашенного круга
     * @param color цвет круга
     * @param x абцисса центра круга
     * @param y ордината центра круга
     * @param r радиус круга
     */
    public FillCircle(Color color, int x, int y, int r) {
        super(color, x, y, r);
    }

    // ************* Implements Methods Of Interface ShapeDraw **************
    
    // Реализуем метод рисования залитого круга
    @Override
    public void draw(Graphics g) {
        g.fillOval(x - width / 2 , y - height / 2, width, height);
    }

    // ************* Implements Methods of Interface ShapeParameter **************
    
    // Фигура закрашенная!
    @JsonbTransient
    @Override
    public boolean isFill() {
        return true;
    }
}
