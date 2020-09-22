/*
 * File:    BoxRecord.java
 * Project: HelloJavaSE
 * Date:    22 сент. 2020 г. 23:34:08
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

/**
 * Запись о коробке (неизменяемая)
 * @param width ширина коробки
 * @param height высота коробки
 * @param length длина коробки
 * @author Igor Morenko
 * @since JDK14
 */
public record BoxRecord(int width, int height, int length) {
    
    // ************* Getters **************
    
    /**
     * Получить периметр коробки
     * @return значение перимета коробки
     */
    public double getPerimeter() {
        return Box.perimeter(width, height, length);
    }
    
    /**
     * Получить площадь поверхности коробки
     * @return значение площади поверхности коробки
     */
    public double getSquareSurface() {
        return Box.squareSurface(width, height, length);
    }

    /**
     * Получить объем коробки
     * @return значение объема коробки
     */
    public double getVolume() {
        return Box.volume(width, height, length);
    }
}
