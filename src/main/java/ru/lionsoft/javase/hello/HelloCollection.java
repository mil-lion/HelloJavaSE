/*
 * File:    HelloCollection.java
 * Project: HelloJavaSE
 * Date:    Jan 28, 2019 8:20:08 AM
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloCollection {
    
    public static void main(String[] args) {
        HelloCollection app = new HelloCollection();
        
        // Целочисленная коллекция
        Collection<Integer> intCol1 = new ArrayList<>();
        intCol1.add(3);
        intCol1.add(5);
        intCol1.add(7);
        intCol1.add(3);
        intCol1.add(10);
        intCol1.add(null);
        app.printCollection("intCol1", intCol1);
        
        // Колекция коробок
        List<Box<Integer>> boxes = new ArrayList<>();
        boxes.add(new Box(Box.TyepSize.LARGE));
        boxes.add(new Box(Box.TyepSize.SMALL)); 
        boxes.add(new Box(Box.TyepSize.MEDIUM));
        boxes.add(new Box(10));
        System.out.println("boxes = " + boxes);

        // Сортировка
        Collections.sort(boxes);
        System.out.println("boxes(asc) = " + boxes);
        
        // Сортировка по убыванию с использование класса сравнения коробок
        Collections.sort(boxes, new BoxComparator(false));
        System.out.println("boxes(desc) = " + boxes);

        // Сортировка по возрастанию и NULL в конце списка
        boxes.set(2, null);
        Collections.sort(boxes, new BoxComparator(true, false));
        System.out.println("boxes(asc, nulls last) = " + boxes);
        
        // Сортировка по убыванию c использованием анонимного класса
        Collections.sort(boxes, new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                if (o1 == o2) return 0;
                if (o1 == null) return -1;
                return -o1.compareTo(o2);
            }
        });
        System.out.println("boxes(desc, anonymouse) = " + boxes);

        // Сортировка по возрастанию c использованием лямбда
        boxes.set(0, new Box(10));
        Collections.sort(boxes, (Box box1, Box box2)->box1.compareTo(box2));
        System.out.println("boxes(asc, lambda) = " + boxes);
        
    }

    public void printCollection(String name, Collection collection) {
        System.out.println(name + " = " + collectionToString(collection));
    }
    
    public String collectionToString(Collection collection) {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        for (Object value : collection) {
            if (i++ > 0) sb.append(", ");
            sb.append(value);
        }
        return sb.append(']').toString();
    }
    
}
