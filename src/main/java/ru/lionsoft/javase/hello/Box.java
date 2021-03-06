/*
 * File:    Box.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 18:39:49
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.awt.Color;
import java.io.Serializable;

/**
 * Класс описывающий объект коробка
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class Box 
        implements Serializable, AutoCloseable, Comparable<Box> { 
    
    private static final long serialVersionUID = 1L;
    
    // ************* Constants **************

    /** Стандартный типоразмер коробки: По умолчанию */
    public static final char TYPE_SIZE_DEFAULT = 'D';
    /** Стандартный типоразмер коробки: Малая */
    public static final char TYPE_SIZE_SMALL = 'S';
    /** Стандартный типоразмер коробки: Средняя */
    public static final char TYPE_SIZE_MEDIUM = 'M';
    /** Стандартный типоразмер коробки: Большая */
    public static final char TYPE_SIZE_LARGE = 'L';
    /** Стандартный типоразмер коробки: Огромная */
    public static final char TYPE_SIZE_EXTRA_LARGE = 'X';

    /**
     * Перечисление стандартных типоразмеров коробки
     */
    public enum TypeSize {
        /** По умолчанию */
        Default,
        /** Малая */
        Small,
        /** Средняя */
        Medium,
        /** Большая */
        Large,
        /** Огромная */
        ExtraLarge
    }
    
    // ************* Static Variable **************
    
    /**
     * Размер кроробки по умолчанию
     */
    public static int defaultSize = 1;
    
    // ************* Properties **************
    
    /**
     * Цвет коробки
     */
    private Color color = Color.BLACK;

    /**
     * Получить цвет коробки
     *
     * @return значение цвета
     */
    public Color getColor() {
        return color;
    }

    /**
     * Установить цвет коробки
     *
     * @param color новое значение цвета
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Ширина коробки
     */
    private int width;

    /**
     * Получить ширину коробки
     *
     * @return значение ширины
     */
    public int getWidth() {
        return width;
    }

    /**
     * Установить ширину коробки
     *
     * @param width новое значение ширины
     */
    public void setWidth(int width) {
        this.width = width;
        hash = 0; // требуется пересчитать хэш-функцию
    }

    /**
     * Высота коробки
     */
    private int height;

    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the value of height
     *
     * @param height new value of height
     */
    public void setHeight(int height) {
        this.height = height;
        hash = 0; // требуется пересчитать хэш-функцию
    }

    /**
     * Длина коробки
     */
    private int length;

    /**
     * Get the value of length
     *
     * @return the value of length
     */
    public int getLength() {
        return length;
    }

    /**
     * Set the value of length
     *
     * @param length new value of length
     */
    public void setLength(int length) {
        this.length = length;
        hash = 0; // требуется пересчитать хэш-функцию
    }
    
    // ************* Getters **************
    
    /**
     * Получить периметр коробки
     * @return значение перимета коробки
     */
    public double getPerimeter() {
        return perimeter(width, height, length);
    }
    
    /**
     * Получить площадь поверхности коробки
     * @return значение площади поверхности коробки
     */
    public double getSquareSurface() {
        return squareSurface(width, height, length);
    }

    /**
     * Получить объем коробки
     * @return значение объема коробки
     */
    public double getVolume() {
        return volume(width, height, length);
    }

    // ************* Static Methods (Functions) **************
    
    /**
     * Вычислить периметр коробки
     * @param width ширина кробки
     * @param height высота коробки
     * @param length длина коробки
     * @return периметр коробки
     */
    public static double perimeter(double width, double height, double length) {
        return 4 * (width + height + length);
    }

    /**
     * Вычислить площадь поверхности коробки
     * @param width ширина кробки
     * @param height высота коробки
     * @param length длина коробки
     * @return площадь поверхности коробки
     */
    public static double squareSurface(double width, double height, double length) {
        return 2 * (width * height + width * length + height * length);
    }

    /**
     * Вычислить объем коробки
     * @param width ширина кробки
     * @param height высота коробки
     * @param length длина коробки
     * @return объем коробки
     */
    public static double volume(double width, double height, double length) {
        return width * height * length;
    }
    
    /**
     * Функция сравнения коробок (по объему)
     * @param b1 ссылка на одну коробку
     * @param b2 ссылка на другую коробку
     * @return 0 если коробки равны,
     *  меньше 0 если первая коробка меньше другой,
     *  больше 0 если первая коробка больше другой
     */
    public static int compare(Box b1, Box b2) {
        if (b1 == b2) return 0;
        if (b1 == null) return -1;
        if (b2 == null) return 1;
        return Double.compare(b1.getVolume(), b2.getVolume());
    }
    
    // ************* Methods **************
    
    /**
     * Метод открытия коробки
     */
    public void open() {
        System.out.println("Коробка открыта!");
    }
    
    /**
     * Метод закрытия коробки
     * (автоматическое закрытие объекта, реализация интерфейса Autoclosable)
     */
    @Override
    public void close() {
        System.out.println("Коробка закрыта!");
    }
    
    // ************* Constructors **************

    /**
     * Конструктор по умолчанию
     */
    public Box() {
        this(defaultSize);
    }

    /**
     * Конструктор коробки с заданными размерами
     * @param width ширина кробки
     * @param height высота коробки
     * @param length длина коробки
     */
    public Box(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    /**
     * Конструктор коробки с заданными размерамии цветом
     * @param width ширина кробки
     * @param height высота коробки
     * @param length длина коробки
     * @param color цвет коробки
     */
    public Box(int width, int height, int length, Color color) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.color = color;
    }
    
    /**
     * Конструктор коробки в виде куба (все стороны равны)
     * @param size размер кробки
     */
    public Box(int size) {
        this(size, size, size);
    }
    
    /**
     * Конструктор коробки в виде куба (все стороны равны)
     * @param size размер кробки
     * @param length длина коробки
     */
    public Box(int size, int length) {
        this(size, size, length);
    }
    
    /**
     * Конструктор коробки стандартных размеров
     * @param typeSize типоразмер кробки
     */
    public Box(TypeSize typeSize) {
        switch (typeSize) {
            case Small:
                width = 1;
                height = 2;
                length = 3;
                break;

            case Medium:
                width = 4;
                height = 5;
                length = 6;
                break;

            case Large:
                width = 7;
                height = 8;
                length = 9;
                break;

            case ExtraLarge:
                width = 10;
                height = 11;
                length = 12;
                break;

            default:
                width = height = length = defaultSize;
        }
    }

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
            case TYPE_SIZE_SMALL -> new Box(1, 2, 3);
            case TYPE_SIZE_MEDIUM -> new Box(4, 5, 6);
            case TYPE_SIZE_LARGE -> new Box(7, 8, 9);
            case TYPE_SIZE_EXTRA_LARGE -> new Box(10, 11, 12);
            default -> new Box(); // default
        };
    }
    
    // ************* Equals & HashCode **************

    /**
     * Значение хэш-функции для коробки (кэш)
     */
    private int hash;
    
    // Функция вычисления значения хэш-функции для коробки
    @Override
    public int hashCode() {
        // значение хэш-функции закэшировано в поле класса
        if (hash == 0) {
            hash = 3;
            hash = 59 * hash + width;
            hash = 59 * hash + height;
            hash = 59 * hash + length;
        }
        return hash;
    }

    // Реализуем метод сравнения коробок (на равенство) по размерам коробки
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
//        if (getClass() != obj.getClass()) {
        if (!(obj instanceof Box)) {
            return false;
        }
        // Use HashCode
        if (hashCode() != obj.hashCode()) {
            return false;
        }
        final Box other = (Box) obj;
        // TODO нужно реализовать правильное сравнение (9 комбинаций) всех сторон со всеми сторонами!
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        return this.length == other.length;
    }
    
    // ************* Implements Interface Comparable<T> **************

    // Реализуем метод сравнения коробок для сортировки коробок
    @Override
    public int compareTo(Box o) {
        // используем уже готовую функцию сравнения двух коробо по объему
        return compare(this, o);
    }
    
    // ************* Cast to String **************

    // Переписываем метод приведения коробки к строке для красивой печати коробки при отладке
    @Override
    public String toString() {
        return "Box{" 
                + "width=" + width 
                + ", height=" + height 
                + ", length=" + length 
                + ", color=" + color 
                + '}';
    }
       
}
