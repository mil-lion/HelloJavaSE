/*
 * File:    FillOval.java
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
 * Класс реализирующий фигуру Закарашенный Овал
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
@Filled
public final class FillOval extends Oval {

    // ************* Constructors **************

    /**
     * Конструктор закрашенного овала по умолчанию
     */
    public FillOval() {
    }

    /**
     * Конструктор закрашенного овала
     * @param color цвет овала
     * @param x абцисса центра овала
     * @param y ордината центра овала
     * @param width ширина овала (горизонтальная ось овала)
     * @param height высота овала (вертикальная ось овала)
     */
    public FillOval(Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height);
    }
    
    // ************* Implements Methods Of Interface ShapeDraw **************
    
    // Реализуем метод рисования залитого овала
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
