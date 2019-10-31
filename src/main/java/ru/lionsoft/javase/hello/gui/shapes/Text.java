/*
 * File:    Text.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 23:00:30
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.shapes;

import java.awt.Color;
import java.awt.Graphics;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Класс реализирующий фигуру Текст
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
@XmlRootElement
public class Text extends AbstractShape {
    
    // ************* Constructors **************

    /**
     * Конструктор текста по умолчанию
     */
    public Text() {
    }

    /**
     * Конструктор текст
     * @param color цвет текста
     * @param x абцисса левой нижней текста
     * @param y ордината левой нижней текста
     * @param text текст
     */
    public Text(Color color, int x, int y, String text) {
        super(color, x, y);
        this.text = text;
    }
    
    // ************* Properties **************

    /**
     * Текст
     */
    protected String text;

    /**
     * Get the value of text
     *
     * @return the value of text
     */
//    @XmlValue
    public String getText() {
        return text;
    }

    /**
     * Set the value of text
     *
     * @param text new value of text
     */
    public void setText(String text) {
        this.text = text;
    }

    // ************* Implements Of Interface ShapeDraw **************
    
    // Реализуем метод рисования текста
    @Override
    public void draw(Graphics g) {
        g.drawString(text, x, y);
    }

    // ************* toString **************

    // Переписываем метод приведения фигуры к строке (используем метод-помощник родителя)
    @Override
    public String toString() {
        return super.toString() + ", text=\"" + text + "\"}";
    }
}
