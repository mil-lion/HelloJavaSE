/*
 * File:    BoxBuilder.java
 * Project: HelloJavaSE
 * Date:    11 сент. 2019 г. 21:07:55
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.awt.Color;


public class BoxBuilder<T extends Number> {

    private T width;
    private T height;
    private T length;
    private Color color = Color.BLACK;

    public BoxBuilder() {
    }

    public BoxBuilder<T> setWidth(T width) {
        this.width = width;
        return this;
    }

    public BoxBuilder<T> setHeight(T height) {
        this.height = height;
        return this;
    }

    public BoxBuilder<T> setLength(T length) {
        this.length = length;
        return this;
    }

    public BoxBuilder<T> setColor(Color color) {
        this.color = color;
        return this;
    }

    public Box<T> createBox() {
        return new Box<T>(width, height, length, color);
    }
    
}
