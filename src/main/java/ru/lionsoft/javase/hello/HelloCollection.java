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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloCollection {
    
    private final Random rnd = new Random();
    
    public static void main(String[] args) {
        HelloCollection app = new HelloCollection();
        
        app.testCollection();
        app.testSet();
        app.testList();
        app.testMap();
        
    }

    public void testCollection() {
        System.out.println("\nru.lionsoft.javase.hello.HelloCollection.testCollection()");
        // ######## Collection ########
        
        // Целочисленная коллекция
        Collection<Integer> intCol1 = new ArrayList<>();
        intCol1.add(3);
        intCol1.add(5);
        intCol1.add(7);
        intCol1.add(Integer.valueOf(3));
        intCol1.add(10);
        intCol1.add(null);
        printCollection("intCol1", intCol1);
    }

    public void testSet() {
        System.out.println("\nru.lionsoft.javase.hello.HelloCollection.testSet()");
        // ######## Set ########
        
        Set<Integer> intSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            intSet.add(rnd.nextInt(20));
        }
        printCollection("intSet", intSet);
        
        Set<Box> boxSet1 = new HashSet<>();
        boxSet1.add(new Box());
        boxSet1.add(null); // ok!
        boxSet1.add(new Box(1, 2, 3));
        boxSet1.add(new Box(3, 2, 1));
        boxSet1.add(new Box(10));
        boxSet1.add(new Box(Box.TypeSize.Default));
        boxSet1.add(new Box(Box.TypeSize.Large));
        boxSet1.add(new Box(Box.TypeSize.Small));
        boxSet1.add(new Box(Box.TypeSize.Medium));
        boxSet1.add(new Box(1, 2, 3));
        boxSet1.add(new Box(1));
        System.out.println("boxSet1.size = " + boxSet1.size());
        printCollection("boxSet1", boxSet1);
        
        Set<Box> boxSet2 = new TreeSet<>();
        boxSet2.add(new Box());
//        boxSet2.add(null); // TreeSet не может содержать элемент null
        boxSet2.add(new Box(1, 2, 3));
        boxSet2.add(new Box(3, 2, 1));
        boxSet2.add(new Box(10));
        boxSet2.add(new Box(Box.TypeSize.Default));
        boxSet2.add(new Box(Box.TypeSize.Large));
        boxSet2.add(new Box(Box.TypeSize.Small));
        boxSet2.add(new Box(Box.TypeSize.Medium));
        boxSet2.add(new Box(1, 2, 3));
        boxSet2.add(new Box(1));
        System.out.println("boxSet2.size = " + boxSet2.size());
        printCollection("boxSet2", boxSet2);
        
    }

    public void testList() {
        System.out.println("\nru.lionsoft.javase.hello.HelloCollection.testList()");
        // ######## List ########
        
        // Список строк
        List<String> strList = new ArrayList<>();
        strList.add("one");
        strList.add("two");
        strList.add("three");
        strList.add("four");
        strList.add("five");
        strList.add(0, "zero");
        printCollection("strList", strList);
        strList.set(2, "два");
        printCollection("strList", strList);
        printList("strList", strList);
        
        // Колекция коробок
        List<Box<Integer>> boxes = new LinkedList<>();
        boxes.add(new Box(Box.TypeSize.Large));
        boxes.add(new Box(Box.TypeSize.Small)); 
        boxes.add(new Box(Box.TypeSize.Medium));
        boxes.add(new Box(10));
        printCollection("boxes", boxes);

        // Сортировка
        Collections.sort(boxes);
        printCollection("boxes(asc)", boxes);
        
        // Сортировка по убыванию с использование класса сравнения коробок
        Collections.sort(boxes, new BoxComparator(false));
        printCollection("boxes(desc)", boxes);

        // Сортировка по возрастанию и NULL в конце списка
        boxes.set(2, null);
        Collections.sort(boxes, new BoxComparator(true, false));
        printCollection("boxes(asc, nulls last)", boxes);
        
        // Сортировка по убыванию c использованием анонимного класса
        Collections.sort(boxes, new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                if (o1 == o2) return 0;
                if (o1 == null) return -1;
                return -o1.compareTo(o2);
            }
        });
        printCollection("boxes(desc, anonymouse)", boxes);

        // Сортировка по возрастанию c использованием лямбда
        boxes.set(0, new Box(10));
        Collections.sort(boxes, (b1, b2) -> b1.compareTo(b2));
        printCollection("boxes(asc, lambda)", boxes);
    }

    public void testMap() {
        System.out.println("\nru.lionsoft.javase.hello.HelloCollection.testMap()");
        // ######## Map ########
        Map<String, Box> dict1 = new HashMap<>();
        Map<Box, String> dict2 = new TreeMap<>();
        
        for (Box.TypeSize typeSize : Box.TypeSize.values()) {
            dict1.put(typeSize.name(), new Box(typeSize));
            dict2.put(new Box(typeSize), typeSize.name());
        }
        printMap("disct1", dict1);
        printMap("disct2", dict2);
    }

    public void printCollection(String name, Collection collection) {
        System.out.println(name + " = " + collectionToString(collection));
    }
    
    public String collectionToString(Collection collection) {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        for (Object element : collection) {
            if (i++ > 0) sb.append(", ");
            sb.append(element);
        }
        return sb.append(']').toString();
    }

    private void printList(String name, List list) {
        StringBuilder sb = new StringBuilder(name).append(" = [");
        int i = 0;
        ListIterator iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()) {
            if (i++ > 0) sb.append(", ");
            Object element = iterator.previous();
            sb.append(element);
        }
        System.out.println(sb.append(" ]").toString());
    }

    public void printMap(String name, Map map) {
        System.out.println(name + ":");
        for (Object e : map.entrySet()) {
            Map.Entry entry = (Map.Entry) e;
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    
}
