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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
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
import ru.lionsoft.javase.hello.gui.util.AnnotatedMethodCache;

/**
 * Пример создания графического окна и отрисовки простых фигур
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class TestGUI extends JComponent {
    
    // TODO: заменить на колекцию!
    private final ShapeDraw[] shapes = new ShapeDraw[14];
    
    // Кэш для списка аннотированных методов классов фигур
    private final AnnotatedMethodCache cache = new AnnotatedMethodCache();

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
        shapes[7] = new Oval(Color.DARK_GRAY, 450, 300, 150, 120);
        shapes[8] = new FillOval(Color.PINK, 450, 300, 130, 100);
        shapes[9] = new Text(Color.BLUE, 410, 110, "Виват Цезарь!");
        shapes[10] = (new Text(Color.DARK_GRAY, 420, 130, "Виват Император!"));
        shapes[11] = new Text(new Color(128, 255, 64), 430, 150, "Привет Мир!");
        shapes[12] = new Circle(new Color(64, 128, 255), 130, 270, 40);
        shapes[13] = new FillCircle(new Color(128, 255, 64), 130, 270, 30);
        
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
            cache.invokeAnnodatedMethods(shape, PreDraw.class, g);
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
            cache.invokeAnnodatedMethods(shape, PostDraw.class, g);
        }
    }
    
    /**
     * Метод анализирует классы всех фигур на аннотацию методов @PreDraw и @PostDraw 
     */
    private void analyzeAnnotationForShapes() {
        System.out.println("@@@@ analyze annotation methods");
//        Set<Class> shapeClasses = new HashSet<>();
//        for (ShapeDraw shape : shapes) {
//            if (shapeClasses.add(shape.getClass())) {
//                cache.add(shape.getClass(), PreDraw.class, PostDraw.class);
//            }
//        }
        Stream.of(shapes)
                .filter((shape) -> shape != null) // skip null
                .map((shape) -> shape.getClass()) // ShapeDraw -> Class
                .distinct()                       // skip duplicate class
                .forEach((clazz) -> cache.add(clazz, PreDraw.class, PostDraw.class));

        // Сортируем методы по аттрибуту 'order' аннотаций PreDraw и PostDraw
        cache.sortAnnotatedMethods(PreDraw.class, new Comparator<Method>() {
            @Override
            public int compare(Method m1, Method m2) {
                return m1.getAnnotation(PreDraw.class).order() - m2.getAnnotation(PreDraw.class).order();
            }
        });
        cache.sortAnnotatedMethods(PostDraw.class,
                (m1, m2) -> m1.getAnnotation(PostDraw.class).order() - m2.getAnnotation(PostDraw.class).order());
    }

    /**
     * Метод вычисления статистики для фигур
     */
    private void calculateStatistics() {
        System.out.println("@@@@ analize shapes and calculate statistics");
        // Опредяем агрегаторы и счетчики
        double sumSquare = 0; // сумма площади всех фигур (необходимое кол-во краски для отрисовки фигур)
        int count = 0; // кол-во фигур
        int lines = 0; // кол-во линий
        int rects = 0; // кол-во прямоугольников
        int ovals = 0; // кол-во овалов
        int texts = 0; // кол-во тесктовых строк
        int fills = 0; // кол-во закрашенных фигур
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
                    fills++;
                    // Фигура закрашена то добавляем площадь
                    sumSquare += param.getSquare();
                }

                // Type
                switch (param.getShapeType()) {
                    case Line:
                        lines++;
                        break;
                        
                    case Rectangle:
                    case Square:
                        rects++;
                        break;
                        
                    case Oval:
                    case Circle:
                        ovals++;
                        break;
                    case Text:
                        texts++;
                        break;
                }
                // JDK12+ (--enable-preview)
//                switch (param.getShapeType()) {
//                    case Line              -> lines++;
//                    case Rectangle, Square -> rects++;
//                    case Oval, Circle      -> ovals++;
//                    case Text              -> texts++;
//                }

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
        System.out.println("texts = " + texts);
        System.out.println("fills = " + fills);
        System.out.println("colors = " + colors);
    }

}
