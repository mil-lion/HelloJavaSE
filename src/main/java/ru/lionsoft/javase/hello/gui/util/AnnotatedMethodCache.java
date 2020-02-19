/*
 * File:    AnnotatedMethodCache.java
 * Project: HelloJavaSE
 * Date:    18 февр. 2020 г. 21:45:23
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2020 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс кэш аннотированных методов для последуещего вызова
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class AnnotatedMethodCache {

    // Журнал для класса
    private static final Logger LOG = Logger.getLogger(AnnotatedMethodCache.class.getName());
    
    // Хранилище коллекции методов помеченных аннотаций для конкретных классов
    private final Map<Class, Map<Class, List<Method>>> cache = new HashMap<>();
    
    /**
     * Добавить аннотированные методы класса в кэш
     * @param objClass ссылка на описание класса
     * @param annotationClasses аннотации методов для анализа (добавления в кэш)
     */
    public void add(Class objClass, Class... annotationClasses) {
        System.out.println("analyze class: " + objClass.getName());
        // Перебираем все публичные методы объекта включая иерархию
        for (Method method : objClass.getMethods()) {
            System.out.println("> method: " + method.getName());
            // Перебираем все аннотации
            for (Class annotationClass : annotationClasses) {
                Annotation annotation = method.getAnnotation(annotationClass);
                if (annotation != null) {
                    // Метод помечен аннотацией
                    addMethodToCache(objClass, annotationClass, method);
                }
            }
        }
    }

    /**
     * Добавить аннотированный метод в кэш если он помечен нужной аннотацией
     * @param objClass ссылка на класс обхекта
     * @param annotationClass класс аннотации метода
     * @param method метод для добваления, если он помечен аннотацией
     */
    private void addMethodToCache(Class objClass, Class annotationClass, Method method) {
        if (!cache.containsKey(objClass)) {
            cache.put(objClass, new HashMap<>());
        }
        if (!cache.get(objClass).containsKey(annotationClass)) {
            cache.get(objClass).put(annotationClass, new ArrayList<>());
        }
        cache.get(objClass).get(annotationClass).add(method);
    }
    
    /**
     * Сортировка вызова методов помеченных  особой аннотацией
     * @param annotationClass класс аннотации
     * @param comparator функциональный интерфейс сравнения методов для сортировки
     */
    public void sortAnnotatedMethods(Class annotationClass, Comparator<Method> comparator) {
        cache.values().forEach((classMethods) -> 
                Collections.sort(classMethods.get(annotationClass), comparator));
    }
    
    /**
     * Вызвать аннотированниые методы у объекта с заданными параметрами
     * @param obj ссылка на объект
     * @param annotationClass аннотация методов
     * @param args параметры для вызова метода
     */
    public void invokeAnnodatedMethods(Object obj, Class annotationClass, Object... args) {
        final Class objClass = obj.getClass();
        if (!cache.containsKey(objClass)
         || !cache.get(objClass).containsKey(annotationClass))
            return;
        
        // Перебираем все анотированные методы
        cache.get(objClass).get(annotationClass).forEach((method) -> {
            System.out.println("invoke Annotated Method: " + method.toGenericString());
            try {
                method.invoke(obj, args);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                System.err.println("Error: Method invoke " + method.getName() + ": " + ex.getMessage());
                LOG.log(Level.SEVERE, "Invoke Method", ex);
            }
        });
    }
    
}
