/*
 * File:    TestGUI.java
 * Project: HelloJavaSE
 * Date:    23 нояб. 2018 г. 21:15:39
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.xml.jaxb;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
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
public class TestXmlGUI extends JComponent {
    
    /** Журнал для класса */
    private static final Logger LOG = Logger.getLogger(TestXmlGUI.class.getName());

    /** Список фигур */
    private final List<ShapeDraw> shapes = new ArrayList<>();
    
    // Кэш для списка аннотированных методов классов
    private final AnnotatedMethodCache cache = new AnnotatedMethodCache();

    /**
     * Конструктор по умолчанию для инициализации фигур
     */
    public TestXmlGUI() {
        System.out.println("@@@@ init()");
        
        // Инициализация списка фигур
        
        // Создаем другие фигуры разработанные другими программистами
        shapes.add(new Line(Color.MAGENTA, 10, 10, 50, 20));
        shapes.add(new Rectangle(Color.BLUE, 200, 200, 150, 80));
        shapes.add(new FillRectangle(Color.CYAN, 210, 210, 130, 60));
        shapes.add(new Oval(Color.DARK_GRAY, 450, 300, 150, 120));
        shapes.add(new FillOval(Color.PINK, 450, 300, 130, 100));
        shapes.add(new Text(Color.BLUE, 410, 110, "Виват Цезарь!"));
        shapes.add(new Text(Color.DARK_GRAY, 420, 130, "Виват Император!"));
        shapes.add(new Text(new Color(128, 255, 64), 430, 150, "Привет Мир!"));
        shapes.add(new Circle(new Color(64, 128, 255), 130, 270, 40));
        shapes.add(new FillCircle(new Color(128, 192, 128), 130, 270, 30));
        
        // Читаем фигуры из XML файла
        File shapesFile = new File("shapes.xml");
        if (shapesFile.exists()) {
            try {
                loadShapesFromXmlFile(shapesFile);
            } catch (JAXBException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        
        // Сортируем фигуры по порядку отрисовки
        Collections.sort(shapes, (s1, s2) -> Integer.compare(s1.getOrder(), s2.getOrder()));

        // Записываем фигуры в XML файл
        try {
            saveXSD("shapes.xsd");
            saveShapesToXmlFile("shapes2.xml");
        } catch (JAXBException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
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
        frame.getContentPane().add(new TestXmlGUI());
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

    /**
     * Сохранить фигуры в файле в XML формате 
     * @param filename имя файла
     * @throws JAXBException ошибка при сохранении
     */
    private void saveShapesToXmlFile(String filename) throws JAXBException {
        System.out.println("@@@@ save shapes to XML file");
        // Создаем контекст JAXB
        JAXBContext context = JAXBContext.newInstance(ShapeXmlRoot.class, AbstractShape.class, Line.class, Rectangle.class, FillRectangle.class, Oval.class, FillOval.class, Text.class);
        // Создаем Marshaller
        Marshaller marshaller = context.createMarshaller();
        // Устанавливаем свойтсва Marshaller
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8"); // можно windows-1251
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // Создаем XML файл
        marshaller.marshal(new ShapeXmlRoot(shapes), new File(filename));
    }
    
    /**
     * Сохранить схему XML фигур в файл XSD
     * @param filename имя файла
     * @throws JAXBException ошибка формирования XSD
     * @throws IOException ошибка ввода вывода
     */
    private void saveXSD(String filename) throws JAXBException, IOException {
        System.out.println("@@@@ save XSD to file");
        // Создаем контекст JAXB
        JAXBContext context = JAXBContext.newInstance(ShapeXmlRoot.class, AbstractShape.class, Line.class, Rectangle.class, FillRectangle.class, Oval.class, FillOval.class, Text.class, Circle.class, FillCircle.class);
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String string, String string1) throws IOException {
//                File file = new File(suggestedFileName);
                File file = new File(filename);
                StreamResult result = new StreamResult(file);
                result.setSystemId(file.toURI().toURL().toString());
                return result;
            }
        });
    }

    /**
     * Загрузить фигуры из файла в XML формате
     * @param file имя файла
     * @throws JAXBException ошибка при чтении файла
     */
    private void loadShapesFromXmlFile(File file) throws JAXBException {
        System.out.println("@@@@ load shapes form XML file");
        // Создаем контекст JAXB
        JAXBContext context = JAXBContext.newInstance(ShapeXmlRoot.class, 
                AbstractShape.class, Line.class, 
                Rectangle.class, FillRectangle.class, 
                Oval.class, FillOval.class, Text.class, 
                Circle.class, FillCircle.class);
        // Создаем Unmarshaller
        Unmarshaller unmarshaller = context.createUnmarshaller();
        // Разбираем XML 
        ShapeXmlRoot root = (ShapeXmlRoot)unmarshaller.unmarshal(file);
        shapes.addAll(root.getShapes());
    }
}
