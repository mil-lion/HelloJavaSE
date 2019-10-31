/*
 * File:    TestGUI.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 21:15:39
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.gui.test;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import ru.lionsoft.javase.hello.gui.PostDraw;
import ru.lionsoft.javase.hello.gui.PreDraw;
import ru.lionsoft.javase.hello.gui.ShapeDraw;
import ru.lionsoft.javase.hello.gui.ShapeDrawCallback;
import ru.lionsoft.javase.hello.gui.ShapeParameter;
import ru.lionsoft.javase.hello.gui.shapes.AbstractShape;
import ru.lionsoft.javase.hello.gui.shapes.Circle;
import ru.lionsoft.javase.hello.gui.shapes.FillCircle;
import ru.lionsoft.javase.hello.gui.shapes.FillOval;
import ru.lionsoft.javase.hello.gui.shapes.FillRectangle;
import ru.lionsoft.javase.hello.gui.shapes.Line;
import ru.lionsoft.javase.hello.gui.shapes.Oval;
import ru.lionsoft.javase.hello.gui.shapes.Rectangle;
import ru.lionsoft.javase.hello.gui.shapes.Text;

/**
 * Пример создания графического окна и отрисовки простых фигур
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class TestGUI extends JComponent {
    
    /**
     * Журнал для класса
     */
    private static final Logger LOG = Logger.getLogger(TestGUI.class.getName());

    // TODO: заменить на колекцию!
    private final ShapeDraw[] shapes = new ShapeDraw[14];
    
    // Кэш для списка аннотированных методов классов
    private final Map<Class, List<Method>> preMethods = new HashMap<>();
    private final Map<Class, List<Method>> postMethods = new HashMap<>();

    /**
     * Конструктор по умолчанию для инициализации фигур
     */
    public TestGUI() {
        System.out.println("@@@@ init()");
        
        // Инициализация списка фигур
        
        // Первые фигуры для теста данного компонента пока другие пишут реализацию классов фигур
        shapes[0] = new ShapeDraw() {
            // Переписываем абстрактный метод отрисовки
            @Override
            public void draw(Graphics g) {
                Color oldColor = g.getColor();
                g.setColor(Color.RED);
                g.drawString("Тест", 20, 20);
                g.setColor(oldColor);
            }
        };
        // for JDK8 - тоже самое, только используя лямбда-выражения
        shapes[1] = (g) -> {
                Color oldColor = g.getColor();
                g.setColor(Color.GREEN);
                g.drawString("Привет Мир!", 40, 40);
                g.setColor(oldColor);
        };

        // for JDK8 - Более короткая запись лямбда-выражений для простых методов
        shapes[2] = (g) -> g.drawString("Виват Император!", 60, 60);
        
        // Можно использовать абстрактный класс
        shapes[3] = new AbstractShape(Color.ORANGE, 80, 80) {
            // Свойства фигуры (поля класса) в дополнение к x, y, color
            final Color borderColor = Color.BLACK;
            final int width = 200;
            final int height = 100;
            
            // Переписываем абстрактный метод отрисовки
            @Override
            public void draw(Graphics g) {
                g.setColor(color);
                g.fillRect(x, y, width, height);
                g.setColor(borderColor);
                g.drawRect(x, y, width, height);
            }
        };
        // Создаем другие фигуры разработанные другими программистами
        shapes[4] = new Line(Color.MAGENTA, 10, 10, 50, 20);
        shapes[5] = new Rectangle(Color.BLUE, 200, 200, 150, 80);
        shapes[6] = new FillRectangle(Color.CYAN, 210, 210, 130, 60);
        shapes[7] = new Oval(Color.DARK_GRAY, 450, 100, 150, 120);
        shapes[8] = new FillOval(Color.PINK, 450, 100, 130, 100);
        shapes[9] = new Text(Color.BLUE, 410, 110, "Виват Цезарь!");
        shapes[10] = (new Text(Color.DARK_GRAY, 420, 130, "Виват Император!"));
        shapes[11] = new Text(new Color(128, 255, 64), 30, 150, "Привет Мир!");
        shapes[12] = new Circle(new Color(64, 128, 255), 130, 70, 40);
        shapes[13] = new FillCircle(new Color(128, 255, 64), 130, 70, 30);
        
        // Сортируем фигуры по порядку отрисовки
        Arrays.sort(shapes, (s1, s2) -> Integer.compare(s1.getOrder(), s2.getOrder()));

        // Анализируем фигуры на аннатоции метов @PreDraw и @PostDraw
        analyzeAnnotationForShapes();
        
        // Считаем статистику по фигурам
        calculateStatistics();
    }
    
    /**
     * Главный метод для запуска приложения из командной строки
     * @param args аргументы программы из командной строки
     */
    public static void main(String[] args) {
        System.out.println("@@@@ main()");
        // Создаем окно
        JFrame frame = new JFrame("Тест отрисовки фигур на экране");
        // Устанавливаем размер окна
        frame.setSize(640, 480);
        // Устанавливаем свойство окна, чтобы при закрытии окна был выход из приложения
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Добавляем наш компонент TestGUI в контейнер компонентов окна
        frame.getContentPane().add(new TestGUI());
        // Показываем окно на экране
        frame.setVisible(true);
    }

    // Переписываем метод JComponent для отрисовки нашего компонениа
    @Override
    public void paint(Graphics g) {
        System.out.println("@@@@ paint()");
        // Перебираем фигуры
        for (ShapeDraw shape : shapes) {
            // Если фигура существет в массиве (в коллекции можно не проверять)
            if (shape == null) continue; // пропускаем 

            System.out.println("draw -> " + shape); // для отладки
            
            // **** Подготовка к рисованию ****
            // Вызываем аннотированные @PostDraw методы
            callAnnotationMethodsForShape(preMethods, shape, g);
            // before JDK8 - надо проверять реализацию интерфейса
            if (shape instanceof ShapeDrawCallback) {
                System.out.println("@@@@ call ShapeDrawCallback.preDraw()!");
                ((ShapeDrawCallback)shape).preDraw(g);
            }
            // for JDK8 - можно вызывать сразу без проверки приведения типа
            shape.preDraw(g);

            // **** Рисование фигуры ****
            System.out.println("@@@@ call " + shape.getClass().getSimpleName() + ".draw()");
            shape.draw(g);
            
            // **** Завершение отрисовки фигуры ****
            // for JDK8 - можно вызывать сразу без проверки приведения типа
            shape.postDraw(g);
            // before JDK8 - надо проверять реализацию интерфейса ShapeDrawCallback
            if (shape instanceof ShapeDrawCallback) {
                System.out.println("@@@@ call ShapeDrawCallback.postDraw()!");
                ((ShapeDrawCallback)shape).postDraw(g);
            }
            // Вызываем аннотированные @PostDraw методы
            callAnnotationMethodsForShape(postMethods, shape, g);
        }
    }
    
    /**
     * Метод анализирует классы всех фигур на аннотацию методов @PreDraw и @PostDraw 
     */
    private void analyzeAnnotationForShapes() {
        for (Object obj : shapes) {
            // Проверка на существование объекта (для коллекции нет необходимости проверять)
            if (obj != null) {
                final Class objClass = obj.getClass();
                // Если в словаре есть описание класса, то анализировать не нужно повторно
                if (preMethods.containsKey(objClass) || postMethods.containsKey(objClass))
                    continue;

                // Перебираем все публичные методы объекта
                for (Method method : objClass.getMethods()) {
                    // @PreDraw
                    PreDraw preDraw = method.getAnnotation(PreDraw.class);
                    if (preDraw != null) {
                        // Метод аннотирован @PreDraw
                        addMethodToMap(preMethods, objClass, method);
                    }
                    // @PostDraw
                    PostDraw postDraw = method.getAnnotation(PostDraw.class);
                    if (postDraw != null) {
                        // Метод аннотирован @PostDraw
                        addMethodToMap(postMethods, objClass, method);
                    }
                }
            }
        }
    }

    /**
     * Метод добавления метода в словарь
     * @param map словарь ассоциаций списка методов с классом
     * @param key ссылка на класс (ключ словаря)
     * @param value ссылка на метод (значение для добавления в список словаря)
     */
    private void addMethodToMap(Map<Class, List<Method>> map, Class key, Method value) {
        List<Method> list = map.get(key);
        if (list == null) {
            // Не было еще ассоциации - создаем новый список методов
            list = new ArrayList<>();
            map.put(key, list);
        }
        list.add(value);
    }

    /**
     * Метод вызова callback методов для фигуры (объекта)
     * @param map ссылка на словарь списка анотированных методов для классов
     * @param obj ссылка на фигуру
     * @param g графический контекст для передачи в метод в качестве параметра
     */
    private void callAnnotationMethodsForShape(Map<Class, List<Method>> map, Object obj, Graphics g) {
        // Получаем список анотированных методов для класса
        List<Method> methods = map.get(obj.getClass());
        // Если нет анотированных методов - выход
        if (methods == null) return; 
        // Перебираем все анотированные методы
        for (Method method : methods) {
            try {
                // Вызываем анотированный метод для фигуры (объекта) и передаем параметр на графический контекст
                System.out.println("invoke Annotation Method: " + method.toGenericString());
                method.invoke(obj, g);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOG.log(Level.SEVERE, "Invoke Method", ex);
            }
        } 
    }

    /**
     * Метод вычисления статистики для фигур
     */
    private void calculateStatistics() {
        // Опредяем агрегаторы и счетчики
        double sumSquare = 0; // сумма площади всех фигур (необходимое кол-во краски для отрисовки фигур)
        int count = 0; // кол-во фигур
        int lines = 0; // кол-во линий
        int rects = 0; // кол-во прямоугольников
        int ovals = 0; // кол-во овалов
        Map<Color, Integer> colors = new HashMap<>(); // счетчики для каждого цвета
        
        // Перебираем все фигуры
        for (ShapeDraw shape : shapes) {
            
            // Проверка на существование объекта (для коллекции нет необходимости проверять)
            if (shape == null) continue;
             
            count++;
            if (shape instanceof Line) {
                Line line = (Line)shape;
                System.out.println("Анализируем " + line.getClass().getSimpleName());
                lines++;
                sumSquare += line.getLineSize() * 1;
                // Color
                Color color = line.getColor();
                Integer cnt = colors.get(color);
                colors.put(color, (cnt == null ? 1 : cnt++));
            }
            if (shape instanceof ShapeParameter) {
                ShapeParameter param = (ShapeParameter) shape;
                System.out.println("Анализируем " + param.getType());
                // Square
                sumSquare += param.getPerimeter() * 1;
                if (param.isFill()) {
                    // Фигура закрашена то добавляем площадь
                    sumSquare += param.getSquare();
                }
                // Type
                switch (param.getShapeType()) {
                    case LINE:
                        lines++;
                        break;
                        
                    case RECTANGLE:
                    case SQUARE:
                        rects++;
                        break;
                        
                    case OVAL:
                    case CIRCLE:
                        ovals++;
                        break;
                }
                // Color
                Color color = param.getColor();
                Integer cnt = colors.get(color);
                colors.put(color, (cnt == null ? 1 : cnt++));
            }
        }
        // Печатаем статистику
        System.out.println("sumSquare = " + sumSquare);
        System.out.println("count = " + count);
        System.out.println("lines = " + lines);
        System.out.println("rects = " + rects);
        System.out.println("ovals = " + ovals);
        System.out.println("colors = " + colors);
    }
}
