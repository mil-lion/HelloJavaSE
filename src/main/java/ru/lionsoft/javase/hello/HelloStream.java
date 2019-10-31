/*
 * File:    HelloStream.java
 * Project: HelloJavaSE
 * Date:    26 янв. 2019 г. 1:34:51
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 *
 * Copyright 2005-2018 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Класс по работе со Stream API
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloStream {

    public static void main(String[] args) {
        HelloStream app = new HelloStream();

        app.begin();
        app.createStream();
        app.forEach();
        app.filter();
        app.map();
        app.flatMap();
        app.sorted();
        app.takeAndDropWhile();
        app.concat();
        app.skipAndLimit();
        app.terminated();
        app.reduce();
    }

    /**
     * Введение в Stream API
     */
    public void begin() {
        System.out.println("### begin");
        // задача: найти в массиве количество всех чисел, которые больше 0
        // <JDK8
        int[] numbers = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        int count = 0;
        for (int i : numbers) {

            if (i > 0) count++;
        }
        System.out.println(count);
        // JDK8+
        // filter() - intermediate (промежуточный)
        // count() - terminal (терминальный)
        long lcount = IntStream.of(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5).filter(w -> w > 0).count();
        System.out.println(lcount);
    }

    /**
     * Создание потока данных
     */
    public void createStream() {
        System.out.println("### createStream");
        
        List<String> cityList = new ArrayList<>();
        Collections.addAll(cityList,  "Париж", "Лондон", "Мадрид");
        cityList.stream() // получаем поток
                .filter(s -> s.length() == 6) // применяем фильтрацию по длине строки
                .forEach(s -> System.out.println(s)); // выводим отфильтрованные строки на консоль

        // переписано по другому
        Stream<String> citiesStream = cityList.stream(); // получаем поток
        citiesStream = citiesStream.filter(s -> s.length() == 6); // применяем фильтрацию по длине строки
        citiesStream.forEach(s -> System.out.println(s)); // выводим отфильтрованные строки на консоль

        // терминальная операция употребляет поток
        // поток закончен, следующие операторы вызовут ошибку
        try {
            long number = citiesStream.count(); // здесь ошибка, так как поток уже употреблен
            System.out.println(number);
        } catch (Exception ex) {
            System.err.println("Ошибка №1!");
        }
        try {
            citiesStream = citiesStream.filter(s->s.length() > 5); // тоже нельзя, так как поток уже употреблен
            citiesStream.forEach(s -> System.out.println(s));
        } catch (Exception ex) {
            System.err.println("Ошибка №2!");
        }

        // Создание потока из массива
        // для Строк (Объектов)
        citiesStream = Arrays.stream(new String[]{"Париж", "Лондон", "Мадрид"}) ;
        citiesStream.forEach(s -> System.out.println(s)); // выводим все элементы массива

        // для примитивных типов данных
        IntStream intStream = Arrays.stream(new int[]{1, 2, 4, 5, 7});
        intStream.forEach(i -> System.out.println(i));

        LongStream longStream = Arrays.stream(new long[]{100, 250, 400, 5_843_787, 237});
        longStream.forEach(l -> System.out.println(l));

        DoubleStream doubleStream = Arrays.stream(new double[] {3.4, 6.7, 9.5, 8.2345, 121});
        doubleStream.forEach(d -> System.out.println(d));

        // использование статического метода Stream.of
        Stream<String> citiesStream1 =Stream.of("Париж", "Лондон", "Мадрид");
        citiesStream1.forEach(s -> System.out.println(s));
        // можно передать массив
        String[] citiesArray = {"Париж", "Лондон", "Мадрид"};
        Stream<String> citiesStream2 = Stream.of(citiesArray);
        citiesStream2.forEach(s -> System.out.println(s));

        IntStream intStream2 = IntStream.of(1, 2, 4, 5, 7);
        intStream2.forEach(i -> System.out.println(i));

        LongStream longStream2 = LongStream.of(100, 250, 400, 5_843_787, 237);
        longStream2.forEach(l -> System.out.println(l));

        DoubleStream doubleStream2 = DoubleStream.of(3.4, 6.7, 9.5, 8.2345, 121);
        doubleStream2.forEach(d -> System.out.println(d));
    }

    // Данные для тестов
    private final String[] cities = {"Париж", "Лондон", "Мадрид","Берлин", "Брюссель"};
    private final Phone[] phones = {
        new Phone("iPhone 8", "Apple", 62000),
        new Phone("iPhone 6 S", "Apple", 54000),
        new Phone("Nokia 9", "Nokia", 35000),
        new Phone("Lumia 950", "Nokia", 45000),
        new Phone("Samsung Galaxy S 6", "Samsung", 40000),
        new Phone("HTC U12", "HTC", 36000)
    };
    private final Phone[] phones2 = {
        new Phone("iPhone X", "Apple", 600),
        new Phone("Pixel 2", "Google", 500),
        new Phone("iPhone 8", "Apple",450),
        new Phone("Nokia 9", "HMD Global",150),
        new Phone("Galaxy S9", "Samsung", 300)
    };
    private final List<String> phoneNames = Arrays.asList("iPhone X", "Nokia 9", "Huawei Nexus 6P",
            "Samsung Galaxy S8", "LG G6", "Xiaomi MI6", "ASUS Zenfone 3", "Sony Xperia Z5", "Meizu Pro 6",
            "Pixel 2");

    /**
     * Перебор
     */
    public void forEach() {
        System.out.println("### forEach");

        Stream<String> citiesStream = Stream.of(cities);
        citiesStream.forEach(s->System.out.println(s));

        Stream<String> citiesStream2 = Stream.of(cities);
        citiesStream2.forEach(System.out::println);
    }

    /**
     * Фильтрация
     */
    public void filter() {
        System.out.println("### filter");

        Stream<String> citiesStream = Stream.of(cities);
        citiesStream.filter(s->s.length() == 6).forEach(System.out::println);

        // Phone filter
        Stream<Phone> phoneStream = Stream.of(phones);

        phoneStream.filter(p -> p.getPrice() < 50000).forEach(p -> System.out.println(p.getName()));
    }

    /**
     * Отображение
     */
    public void map() {
        System.out.println("### map");

        // преобразование от типа Phone к типу String
        Stream<Phone> phoneStream = Stream.of(phones);
        phoneStream
                .map(p -> p.getName()) // помещаем в поток только названия телефонов
                .forEach(s -> System.out.println(s));

        phoneStream = Stream.of(phones);
        phoneStream
                .map(p-> "название: " + p.getName() + " цена: " + p.getPrice())
                .forEach(System.out::println);

        // преобразование от типа Phone к типу Integer
        phoneStream = Stream.of(phones);
        int minPrice = phoneStream.map(p -> p.getPrice()).min(Integer::compareTo).get();
        System.out.println("minPrice = " + minPrice);
    }

    /**
     * Плоское отображение
     */
    public void flatMap() {
        System.out.println("### flatMap");

        // установить для каждого телефона цену со скидкой и цену без скидки
        Stream<Phone> phoneStream = Stream.of(phones);
        phoneStream
                .flatMap(p->Stream.of(
                        String.format("название: %s  цена без скидки: %d", p.getName(), p.getPrice()),
                        String.format("название: %s  цена со скидкой: %d", p.getName(), p.getPrice() - (int)(p.getPrice() * 0.1))
                ))
                .forEach(System.out::println);

    }

    /**
     * Сортировка
     */
    public void sorted() {
        System.out.println("### sorted");

        // простая сортировка по возрастанию
        phoneNames.stream()
                .filter(p -> p.length() < 12)
                .sorted() // сортировка по возрастанию
                .forEach(System.out::println);

        // сортировка потока обеъктов Phone
        System.out.println("# by name asc");
        Stream<Phone> phoneStream = Stream.of(phones2);
        phoneStream
                .sorted(new PhoneComparator())
                .forEach(p->System.out.printf("%s (%s) - $%d \n",
                        p.getName(), p.getCompany(), p.getPrice()));


        // сортировка потока обеъктов Phone по убыванию цены
        System.out.println("# by price desc");
        phoneStream = Stream.of(phones2);
        phoneStream
//                .sorted((a, b) -> -Integer.compare(a.getPrice(), b.getPrice())) // по убыванию
//                .sorted((a, b) -> Integer.compare(b.getPrice(), a.getPrice())) // по убыванию
//                .sorted(Comparator.comparingInt(Phone::getPrice)) // по возрастанию
                .sorted(Comparator.comparingInt(Phone::getPrice).reversed()) // по убыванию
                .forEach(p->System.out.printf("%s (%s) - $%d \n",
                        p.getName(), p.getCompany(), p.getPrice()));
    }

    /**
     * Получение подпотока и объединение потоков
     */
    public void takeAndDropWhile() {
        System.out.println("### takeAndDropWhile");

        // JDK9+
        System.out.println("# takeWhile");
        // выбираем из потока числа, пока они меньше нуля
        Stream<Integer> numbers = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers.takeWhile(n -> n < 0).forEach(System.out::println);

        // выбираем из потока все элементы отрицательные
        System.out.println("#");
        numbers = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers.sorted().takeWhile(n -> n < 0).forEach(System.out::println);

        System.out.println("# dropWhile");
        // пропускаем элементы потока, которые соответствуют условию до тех пор, 
        // пока не встретит элемент, который НЕ соответствует условию
        numbers = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers.dropWhile(n -> n < 0).forEach(System.out::println);

        System.out.println("#");
        numbers = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers.sorted().dropWhile(n -> n < 0).forEach(System.out::println);
    }
    
    /**
     * Объедиение потоков
     */
    public void concat() {
        System.out.println("### concat");
        
        Stream<String> people1 = Stream.of("Tom", "Bob", "Sam");
        Stream<String> people2 = Stream.of("Alice", "Kate", "Sam");
         
        Stream.concat(people1, people2).forEach(System.out::println);
        
        System.out.println("# distinct");
        Stream<String> people = Stream.of("Tom", "Bob", "Sam", "Tom", "Alice", "Kate", "Sam");
        people.distinct().forEach(System.out::println);
    }
    
    /**
     * Методы skip и limit
     */
    public void skipAndLimit() {
        System.out.println("### skipAndLimit");
        Stream<String> phoneStream = Stream.of("iPhone 6 S", "Lumia 950", "Samsung Galaxy S 6", "LG G 4", "Nexus 7");
         
        phoneStream
                .skip(1)
                .limit(2)
                .forEach(System.out::println);
        
        System.out.println("#");
        List<String> phoneList = new ArrayList<>();
        phoneList.addAll(Arrays.asList(new String[] {
            "iPhone 6 S", "Lumia 950", "Huawei Nexus 6P",
            "Samsung Galaxy S 6", "LG G 4", "Xiaomi MI 5",
            "ASUS Zenfone 2", "Sony Xperia Z5", "Meizu Pro 5",
            "Lenovo S 850"
        }));
         
        int pageSize = 3; // количество элементов на страницу
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Введите номер страницы: ");
            int page = scanner.nextInt();
            
            if (page < 1) break; // если число меньше 1, выходим из цикла
             
            phoneList.stream()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .forEach(System.out::println);
       }
    }
    
    /**
     * Операции сведения
     */
    public void terminated() {
        System.out.println("### terminated");
        
        System.out.println("# count");
        List<String> names = new ArrayList<>();
        names.addAll(Arrays.asList(new String[]{"Tom", "Sam", "Bob", "Alice"}));
        
        System.out.println(names.stream().count()); // 4
         
        // количество элементов с длиной не больше 3 символов
        System.out.println(names.stream().filter(n -> n.length() <= 3).count()); // 3
        
        System.out.println("# findFirst & findAny");
        Optional<String> first = names.stream().findFirst();
        System.out.println(first.get());   // Tom

        Optional<String> findAny = names.stream().findAny();
        System.out.println(findAny.get()); // Tom

        System.out.println("# allMatch, anyMatch, noneMatch");
        // есть ли в потоке строка, длина которой больше 3
        boolean any = names.stream().anyMatch(s -> s.length() > 3);
        System.out.println(any); // true
         
        // все ли строки имеют длину в 3 символа
        boolean all = names.stream().allMatch(s -> s.length() == 3);
        System.out.println(all); // false
         
        // НЕТ ЛИ в потоке строки "Bill". Если нет, то true, если есть, то false
        boolean none = names.stream().noneMatch(s -> s.equals("Bill"));
        System.out.println(none);   // true
        
        System.out.println("# min & max");
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
         
        Optional<Integer> min = numbers.stream().min(Integer::compare);
        Optional<Integer> max = numbers.stream().max(Integer::compare);
        System.out.println(min.get());  // 1
        System.out.println(max.get());  // 9
     
        System.out.println("# min & max Price of Phones");
        List<Phone> phoneList = Arrays.asList(phones);
         
        Phone minPhone = phoneList.stream().min(Phone::compare).get();
        Phone maxPhone = phoneList.stream().max(Phone::compare).get();
        System.out.printf("MIN Name: '%s' Price: %d \n", minPhone.getName(), minPhone.getPrice());
        System.out.printf("MAX Name: '%s' Price: %d \n", maxPhone.getName(), maxPhone.getPrice());
    }
    
    /**
     * Метод reduce
     */
    public void reduce() {
        System.out.println("### reduce");
        
        Stream<Integer> numbersStream = Stream.of(1, 2, 3, 4, 5, 6);
        Optional<Integer> result = numbersStream.reduce((x, y) -> x * y);
        System.out.println(result.get()); // 720
        
        Stream<String> wordsStream = Stream.of("мама", "мыла", "раму");
        Optional<String> sentence = wordsStream.reduce((x, y) -> x + " " + y);
        System.out.println(sentence.get());
        
        wordsStream = Stream.of("мама", "мыла", "раму");
        String sentenceString = wordsStream.reduce("Результат:", (x, y) -> x + " " + y);
        System.out.println(sentenceString); // Результат: мама мыла раму
        
        // в качестве первого параметра идет значение по умолчанию - 0. 
        // Второй параметр производит бинарную операцию, которая получает 
        // промежуточное значение - суммарную цену текущего и предыдущего телефонов. 
        // Третий параметр представляет бинарную операцию, которая суммирует все 
        // промежуточные вычисления.
        Stream<Phone> phoneStream = Stream.of(phones);
        int sum = phoneStream.reduce(0, 
            (x, y) -> {
                if (y.getPrice() < 50000)
                    return x + y.getPrice();
                else
                    return x + 0;
            }, 
            (x, y) -> x + y
        );
        System.out.println(sum); // 156000
    }
    
    /**
     * Тип Optional
     */
    public void optional() {
        System.out.println("### optional");
        
    }
    
    /**
     * Класс для описания телефонов
     */
    private static class Phone {

        private String name;
        private String company;
        private int price;

        public Phone(String name, String company, int price) {
            this.name = name;
            this.company = company;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Phone{" +
                    "name='" + name + '\'' +
                    ", company='" + company + '\'' +
                    ", price=" + price +
                    '}';
        }
        
        public static int compare (Phone p1, Phone p2) {
            return p1.getPrice() > p2.getPrice() ? 1 : -1;
        }
    }
    
    /**
     * Класс компаратор PhoneComparator, предназначен для сортировки объектов Phone по полю name (без учета регистра)
     */
    private class PhoneComparator implements Comparator<Phone> {

        @Override
        public int compare(Phone a, Phone b) {
            return a.getName().toUpperCase().compareTo(b.getName().toUpperCase());
        }
    }
}
