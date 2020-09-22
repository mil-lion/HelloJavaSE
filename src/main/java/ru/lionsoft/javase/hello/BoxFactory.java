/*
 * File:    BoxFactory.java
 * Project: HelloJavaSE
 * Date:    22 сент. 2020 г. 22:44:44
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

/**
 * Фабрика коробок
 * @author Igor Morenko
 */
public class BoxFactory {
    
    // ************* Static Constructors (Method Factory) **************
    
    /**
     * Конструктор коробки заданных размеров 
     * (используется статический метод)
     * @param width ширина коробки
     * @param height высота коробки
     * @param length длина коробки
     * @return новая коробка с заданными размерами
     */
    public static Box createBox(int width, int height, int length) {
        return new Box(width, height, length);
    }

    /**
     * Конструктор коробки стандартных размеров 
     * (используется статический метод)
     * @param type тип стандартной коробки
     * @return созданная коробка
     */
    public static Box createStandardBox(char type) {
        return switch (type) {
            case Box.TYPE_SIZE_SMALL  -> new Box(1, 2, 3);
            case Box.TYPE_SIZE_MEDIUM -> new Box(4, 5, 6);
            case Box.TYPE_SIZE_LARGE  -> new Box(7, 8, 9);
            case Box.TYPE_SIZE_EXTRA_LARGE -> new Box(10, 11, 12);
            default -> new Box(); // default
        };
    }

}
