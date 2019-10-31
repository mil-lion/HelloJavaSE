/*
 * File:    BoxComparator.java
 * Project: HelloJavaSE
 * Date:    Feb 1, 2019 10:15:24 PM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.util.Comparator;

/**
 * Класс для сравнения коробок (для сортировки)
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class BoxComparator implements Comparator<Box> {

    /**
     * Определяет знак сортировки
     */
    private final int sign;
    
    /**
     * Определяет положение NULL в сортировке
     */
    private final int nulls;

    /**
     * Конструктор класса сравнения коробок
     * @param asc сравнение по возрастанию (для сортировки)
     */
    public BoxComparator(boolean asc) {
        this(asc, true);
    }
    
    /**
     * Конструктор класса сравнения коробок с учетом NULL значений
     * @param asc равнение по возрастанию (для сортировки)
     * @param nullsFirst null значения первые (в сортировке)
     */
    public BoxComparator(boolean asc, boolean nullsFirst) {
        sign = asc ? 1 : -1;
        nulls = nullsFirst ? -1 : 1;
    }
     
    // ***************** Implements interface Comparator *****************
    
    /*
     * Сравнение коробок
     *  0 - коробки равны
     * <0 - первая коробка меньше второй
     * >0 - первая коробка больше второй
     */
    @Override
    public int compare(Box o1, Box o2) {
        // одна и таже коробка или обе ссылки null
        if (o1 == o2) return 0;
        // первая ссылка на коробку null
        if (o1 == null) return nulls;
        // сравнение коробок
        return sign * o1.compareTo(o2);
    }
    
}
