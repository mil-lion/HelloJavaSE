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
import java.util.Objects;

/**
 * Класс описывающий объект коробка
 * @param <T> тип данных измерения коробки (ширина, высота, длина)
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class Box<T extends Number> 
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

    /**
     * Перечисление стандартных типоразмеров коробки
     */
    public static enum TypeSize {
        /** По умолчанию */
        Default,
        /** Малая */
        Small,
        /** Средняя */
        Medium,
        /** Большая */
        Large
    }
    
    // ************* Static Variable **************
    
    /**
     * Размер кроробки по умолчанию
     */
    public static Double defaultSize = 0.0;
    
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
    private T width;

    /**
     * Получить ширину коробки
     *
     * @return значение ширины
     */
    public T getWidth() {
        return width;
    }

    /**
     * Установить ширину коробки
     *
     * @param width новое значение ширины
     */
    public void setWidth(T width) {
        this.width = width;
        hash = 0; // требуется пересчитать хэш-функцию
    }

    /**
     * Высота коробки
     */
    private T height;

    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public T getHeight() {
        return height;
    }

    /**
     * Set the value of height
     *
     * @param height new value of height
     */
    public void setHeight(T height) {
        this.height = height;
        hash = 0; // требуется пересчитать хэш-функцию
    }

    /**
     * Длина коробки
     */
    private T length;

    /**
     * Get the value of length
     *
     * @return the value of length
     */
    public T getLength() {
        return length;
    }

    /**
     * Set the value of length
     *
     * @param length new value of length
     */
    public void setLength(T length) {
        this.length = length;
        hash = 0; // требуется пересчитать хэш-функцию
    }
    
    // ************* Getters **************
    
    /**
     * Получить периметр коробки
     * @return значение перимета коробки
     */
    public double getPerimeter() {
        return perimeter(nvl(width), nvl(height), nvl(length));
    }
    
    /**
     * Получить площадь поверхности коробки
     * @return значение площади поверхности коробки
     */
    public double getSquareSurface() {
        return squareSurface(nvl(width), nvl(height), nvl(length));
    }

    /**
     * Получить объем коробки
     * @return значение объема коробки
     */
    public double getVolume() {
        return volume(nvl(width), nvl(height), nvl(length));
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
     * Функция получения значения {@code double} для размера коробки
     * @param <T> тип измерения коробки
     * @param size разиер коробки
     * @return 0 если размер {@code null} иначе размер коробки типа {@code double}
     */
    private static <T extends Number> double nvl(T size) {
        return size != null ? size.doubleValue() : 0.0;
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
        this((T)defaultSize, (T)defaultSize, (T)defaultSize);
    }

    /**
     * Конструктор коробки с заданными размерами
     * @param width ширина кробки
     * @param height высота коробки
     * @param length длина коробки
     */
    public Box(T width, T height, T length) {
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
    public Box(T width, T height, T length, Color color) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.color = color;
    }
    
    /**
     * Конструктор коробки в виде куба (все стороны равны)
     * @param size размер кробки
     */
    public Box(T size) {
        this(size, size, size);
    }
    
    /**
     * Конструктор коробки стандартных размеров
     * @param type типоразмер кробки
     */
    public Box(TypeSize type) {
        switch (type) {
            case Small:
                width = (T)Integer.valueOf(1);
                height = (T)Integer.valueOf(2);
                length = (T)Integer.valueOf(3);
                break;

            case Medium:
                width = (T)Integer.valueOf(4);
                height = (T)Integer.valueOf(5);
                length = (T)Integer.valueOf(6);
                break;

            case Large:
                width = (T)Integer.valueOf(7);
                height = (T)Integer.valueOf(8);
                length = (T)Integer.valueOf(9);
                break;

            default:
                width = (T)defaultSize;
                height = (T)defaultSize;
                length = (T)defaultSize;
        }
    }

    // ************* Static Constructors (Factory) **************
    
    /**
     * Конструктор коробки заданных размеров 
     * (используется статический метод)
     * @param <T> тип измерения коробки
     * @param width ширина коробки
     * @param height высота коробки
     * @param length длина коробки
     * @return новая коробка с заданными размерами
     */
    public static <T extends Number> Box<T> createBox(T width, T height, T length) {
        return new Box<>(width, height, length);
    }

    /**
     * Конструктор коробки стандартных размеров 
     * (используется статический метод)
     * @param type тип стандартной коробки
     * @return созданная коробка
     */
    public static Box createStandardSizeBox(char type) {
        switch (type) {
            case TYPE_SIZE_SMALL:
                return createBox(1, 2, 3);
                
            case TYPE_SIZE_MEDIUM:
                return createBox(4, 5, 6);
                
            case TYPE_SIZE_LARGE:
                return createBox(7, 8, 9);
        }
        return new Box(); // default
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
            hash = 59 * hash + Double.hashCode(nvl(width));
            hash = 59 * hash + Double.hashCode(nvl(height));
            hash = 59 * hash + Double.hashCode(nvl(length));
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
        if (hashCode() != obj.hashCode()) {
            return false;
        }
        final Box<?> other = (Box<?>) obj;
        // TODO нужно реализовать правильное сравнение (9 комбинаций) всех сторон со всеми сторонами!
        if (nvl(this.width) != nvl(other.width)) {
            return false;
        }
        if (nvl(this.height) != nvl(other.height)) {
            return false;
        }
        return nvl(this.length) == nvl(other.length);
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
