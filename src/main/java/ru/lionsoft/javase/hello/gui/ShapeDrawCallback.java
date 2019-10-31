/*
 * File:    ShapeDrawCallback.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 21:04:09
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui;

import java.awt.Graphics;

/**
 * Интерфейс описывающий методы подготовки и окончания отрисовки фигур
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public interface ShapeDrawCallback {
    
    /**
     * Метод подготовки к рисованию фигуры (вызывается перед отрсовкой фигуры)
     * @param g графический контекст
     */
    void preDraw(Graphics g);

    /**
     * Метод завершения рисования фигуры (вызывается после отрисоки фигуры)
     * @param g графический контекст
     */
    void postDraw(Graphics g);
}
