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

/**
 * Строитель коробок
 * @author Igor Morenko
 */
public class BoxBuilder {

    // ***************** Fields ******************
    
    private int width;
    private int height;
    private int length;
    private Color color = Color.BLACK;

    // ***************** Constructors ******************

    public BoxBuilder() {
    }

    public BoxBuilder(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }
    
    public BoxBuilder(Box box) {
        width = box.getWidth();
        height = box.getHeight();
        length = box.getLength();
        color = box.getColor();
    }

    // ***************** Setters ******************
    
    public BoxBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public BoxBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public BoxBuilder setLength(int length) {
        this.length = length;
        return this;
    }

    public BoxBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    // ***************** Build ******************

    public Box createBox() {
        return new Box(width, height, length, color);
    }
    
}
