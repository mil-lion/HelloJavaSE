/*
 * File:    BoxImmutable.java
 * Project: HelloJavaSE
 * Date:    22 сент. 2020 г. 23:27:43
 * Author:  Igor Morenko
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

/**
 * Неизменяемый класс коробки
 * @author Igor Morenko
 */
public class BoxImmutable {
    
    // ********************* Fields **********************
    
    private final int width;
    private final int height;
    private final int length;
    
    // ********************* Constructors **********************
    
    public BoxImmutable(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }
    
    // ********************* Getters **********************

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int length() {
        return length;
    }

    // ********************* Equals & HashCode **********************

    private int hash; // cache hashCode
    
    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = 7;
            hash = 43 * hash + this.width;
            hash = 43 * hash + this.height;
            hash = 43 * hash + this.length;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (hashCode() != obj.hashCode()) {
            return false;
        }
        final BoxImmutable other = (BoxImmutable) obj;
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        return this.length == other.length;
    }

    // ********************* Cast to String **********************

    @Override
    public String toString() {
        return "BoxImmutable{" 
                + "width=" + width 
                + ", height=" + height 
                + ", length=" + length 
                + '}';
    }
        
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
