/*
 * File:    HelloJAXP.java
 * Project: HelloJavaSE
 * Date:    11 сент. 2019 г. 23:07:36
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.xml.jaxp;

import java.awt.Color;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.lionsoft.javase.hello.Box;

/**
 * Пример работы с XML документами с помощью JAXP
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloJAXP {
    
    /**
     * Главный метод программы
     * @param args аргументы командной строки
     * @throws Exception ошибки работы с XML
     */
    public static void main(String[] args) throws Exception {
        HelloJAXP app = new HelloJAXP();
        
        // Write Box to XML File
        Box box1 = new Box(1, 2, 3, new Color(63, 127, 255));
        app.writeBoxToXmlFile(box1, "box.xml");
        System.out.println("write box: " + box1);
        
        // Read Box From XML File
        Box box2 = app.readBoxFromXmlFile("box.xml");
        System.out.println("read box: " + box2);
       
    }

    /*
    DOM Tree for class Box
    xmlDoc - XML document
      +- box - root element "box"
           +- width - element "width"
                +- 1 - text node
           +- height - element "height"
                +- 2 - text node
           +- length - element "length"
                +- 3 - text node
           +- color - element "color"
                +- red   - attribute "red"
                +- green - attribute "green"
                +- blue  - attribute "blue"
    
     */
    
    /**
     * Записать обхект Box в XML файл
     * @param box ссылка на коробку
     * @param filename имя файла XML
     * @throws ParserConfigurationException ошибка конфигурации парсера
     * @throws TransformerException ошибка преобразования DOM дерева в файл
     */
    public void writeBoxToXmlFile(Box box, String filename) throws ParserConfigurationException, TransformerException {
        Document xmlDoc = createDomTree(box);
        writeXmlDomTreeToFile(xmlDoc, filename);
    }

    /**
     * Создание DOM дерева для коробки
     * @param box ссылка на коробку
     * @return ссылка XML документ
     * @throws ParserConfigurationException ошибка конфигурации парсера
     */
    private Document createDomTree(Box box) throws ParserConfigurationException {
        // Create XML Document Builder
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // Create XML Document
        Document xmlDoc = builder.newDocument();
        // Create root element
        Element root = xmlDoc.createElement("box");
        xmlDoc.appendChild(root);
        // Create text elements for Box
        root.appendChild(createTextElement(xmlDoc, "width", box.getWidth().toString()));
        root.appendChild(createTextElement(xmlDoc, "height", box.getHeight().toString()));
        root.appendChild(createTextElement(xmlDoc, "length", box.getLength().toString()));
        // Create element Color
        Element color = xmlDoc.createElement("color");
        color.setAttribute("red", Integer.toString(box.getColor().getRed()));
        color.setAttribute("green", Integer.toString(box.getColor().getGreen()));
        color.setAttribute("blue", Integer.toString(box.getColor().getBlue()));
        root.appendChild(color);
        
        return xmlDoc;
    }

    /**
     * Создать элемент с текстом
     * @param doc ссылка на XML документ
     * @param name имя элемента
     * @param value значение элемента
     * @return ссылка на созданный элемент
     */
    private Element createTextElement(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }
    
    /**
     * Запись XML документа (DOM дерева) в файл
     * @param xmlDoc ссылка на XML документа
     * @param filename имя файла
     * @throws TransformerConfigurationException ошибка конфигурации преобразователя
     * @throws TransformerException ошибка преобразования
     */
    private void writeXmlDomTreeToFile(Document xmlDoc, String filename) throws TransformerConfigurationException, TransformerException {
        // Source
        Source srcXml = new DOMSource(xmlDoc);
        // Result
        Result dstFile = new StreamResult(filename);
        // Create Transormer
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(srcXml, dstFile);
    }

    /**
     * Чтение объекта Box из XML файл
     * @param filename имя файла
     * @return прочитанный объект которбки
     * @throws ParserConfigurationException ошибка конфигурации парсера
     * @throws SAXException ошибка парсера
     * @throws IOException ошибка ввода/вывода
     */
    public Box readBoxFromXmlFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        Box box = new Box();
        // Create XML Document Builder
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // Parse XML file
        Document xmlDoc = builder.parse(filename);
        // Get root element
        Element root = xmlDoc.getDocumentElement();
        System.out.println("root element: " + root.getTagName());
        // Child nodes of root element
        NodeList rootChilds = root.getChildNodes();
        for (int i = 0; i < rootChilds.getLength(); i++) {
            Node node = rootChilds.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("elemnt: " + element.getTagName());
                switch (element.getTagName()) {
                    case "width":
                        String value = getTextElementValue(element);
                        box.setWidth(Integer.parseInt(value));
                        break;
                    
                    case "height":
                        value = getTextElementValue(element);
                        box.setHeight(Integer.parseInt(value));
                        break;
                    
                    case "length":
                        value = getTextElementValue(element);
                        box.setLength(Integer.parseInt(value));
                        break;
                    
                    case "color":
                        int red = Integer.parseInt(element.getAttribute("red"));
                        int green = Integer.parseInt(element.getAttribute("green"));
                        int blue = Integer.parseInt(element.getAttribute("blue"));
                        box.setColor(new Color(red, green, blue));
                        break;
                }
            }
        }
        return box;
    }

    /**
     * Получить значение элемента с текстом
     * @param element ссылка на элемент
     * @return значение элемента с текстом
     */
    private String getTextElementValue(Element element) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                return node.getNodeValue();
            }
        }
        return null;
    }
    
}
