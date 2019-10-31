/*
 * File:    HelloJpa.java
 * Project: HelloJavaSE
 * Date:    30 окт. 2019 г. 21:04:41
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.db.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.lionsoft.javase.hello.db.jpa.entities.Customer;
import ru.lionsoft.javase.hello.db.jpa.entities.DiscountCode;
import ru.lionsoft.javase.hello.db.jpa.entities.MicroMarket;

/**
 * Пример работы с СУБД через технологию JPA
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloJpa {
    
    // менеджер сущностей
    private EntityManager em;
    
    public static void main(String[] args) {
        HelloJpa app = new HelloJpa();
    
        // Создаем менеджер сущностей для БД HelloJavaSEPU
        app.createEntityManager("HelloJavaSEPU");
        
        
        // Ищем скидку по первичную ключу
        DiscountCode discountCode = app.findDiscountCodeById("H");
        System.out.println("discountCode = " + discountCode);
        
        // Ищем магазин по именованному запросу (по коду магазина)
        MicroMarket microMarket = app.findMicroMarketById("10095");
        System.out.println("microMarket = " + microMarket);
        
        // Получаем список клиентов по скиде с кодом 'H'
        List<Customer> customers = app.findCustomerByDiscountCode("H");
        System.out.println("\nCustomers with discount code 'H':");
        for (Customer customer: customers) {
            System.out.println(customer);
        }
        
        // Добавляем нового клиента
        Customer newCustomer = new Customer((int)System.currentTimeMillis());
        newCustomer.setDiscountCode(discountCode);
        newCustomer.setMicroMarket(microMarket);
        newCustomer.setName("XXX");
        app.insertCustomer(newCustomer);
        
        // Изменяем имя клиента
        newCustomer.setName("YYY");
        app.updateCustomer(newCustomer);
        
        // Удаляем клиента
        app.deleteCustomer(newCustomer);
        
        // Поиск клиентов с помощью построителя запросов
        customers = app.findCustomerByName("XXX");
        System.out.println("\nCustomers:");
        for (Customer customer: customers) {
            System.out.println(customer);
        }
        
    }

    /**
     * Создаем менеджер сущностей
     * @param persistenceUnitName имя единицы хранения
     */
    private void createEntityManager(String persistenceUnitName) {
        em = Persistence
                .createEntityManagerFactory(persistenceUnitName)
                .createEntityManager();
    }

    /**
     * Поиск по первичному ключу (поиск скидки по коду)
     * @param code код скидки
     * @return найднная скидка
     */
    private DiscountCode findDiscountCodeById(String code) {
        return em.find(DiscountCode.class, code);
    }

    /**
     * Поиск по именованному запросу (поиск магазина по коду)
     * @param zip код магазина
     * @return найденный магазин
     */
    private MicroMarket findMicroMarketById(String zip) {
        TypedQuery<MicroMarket> query = em.createNamedQuery("MicroMarket.findByZipCode", MicroMarket.class);
        query.setParameter("zipCode", zip);
        return query.getSingleResult();
    }

    /**
     * Поиск по произвольному запросу (поиск клиентов по коду скидки)
     * @param discountCode код скидки
     * @return список клиентов
     */
    private List<Customer> findCustomerByDiscountCode(String discountCode) {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.discountCode.code = :discountCode");
        query.setParameter("discountCode", discountCode);
        return query.getResultList();
    }

    /**
     * Поиск клиентов с помощью построителя запросов
     * @param name имя клиента
     * @return список клиентов
     */
    private List<Customer> findCustomerByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);
        cq.select(root);
        cq.where(cb.equal(root.get("name"), name));
        return em.createQuery(cq).getResultList();
    }

    /**
     * Метод добавления нового клиента
     * @param entity новый клиент (сущность)
     */
    private void insertCustomer(Customer entity) {
        em.getTransaction().begin(); // начинаем транзакцию
        em.persist(entity);
        em.getTransaction().commit(); // фиксируем транзакцию
    }

    /**
     * Обнавляем информацию о клиенте
     * @param entity информация о клиенте
     */
    private Customer updateCustomer(Customer entity) {
        Customer newEntity;
        em.getTransaction().begin(); // начинаем транзакцию
        newEntity = em.merge(entity);
        em.getTransaction().commit(); // фиксируем транзакцию
        return newEntity;
    }

    /**
     * Удаляем информацию о клиенте
     * @param entity информация о клиенте
     */
    private void deleteCustomer(Customer entity) {
        em.getTransaction().begin(); // начинаем транзакцию
        em.remove(em.merge(entity));
        em.getTransaction().commit(); // фиксируем транзакцию
    }

}
