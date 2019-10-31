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
import java.util.stream.Stream;
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
import ru.lionsoft.javase.hello.Box;

/**
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloJAXP {
    
    public static void main(String[] args) throws Exception {
        HelloJAXP app = new HelloJAXP();
        
        // Write Box to XML File
        Box box1 = new Box(1, 2, 3, new Color(63, 127, 255));
        app.writeBoxToXmlFile(box1, "box.xml");
        
        // Read Box From XML File
        Box box2 = app.readBoxFromXmlFile("box.xml");
        System.out.println("box = " + box2);
       
    }

    /*
    DOM Tree for class Box
    xml - XML document
      +- box - root element "box"
           +- width - element "width"
                +- 1 - text node
           +- height - element "height"
                +- 2 - text node
           +- length - element "length"
                +- 3 - text node
           +- color - element "color"
                +- red - attribute "red"
                +- green - attribute "green"
                +- blue - attribute "blue"
    
     */
    
    private void writeBoxToXmlFile(Box box, String filename) throws ParserConfigurationException, TransformerException {
        Document xmlDoc = createDomTree(box);
        writeXmlDomTreeToFile(xmlDoc, filename);
    }

    private Box readBoxFromXmlFile(String filename) throws ParserConfigurationException {
        // Create XML Document Builder
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
        root.appendChild(XmlTextElement.createTextElement(xmlDoc, "width", box.getWidth().toString()));
        root.appendChild(XmlTextElement.createTextElement(xmlDoc, "height", box.getHeight().toString()));
        root.appendChild(XmlTextElement.createTextElement(xmlDoc, "length", box.getLength().toString()));
        // Create element Color
        Element color = xmlDoc.createElement("color");
        color.setAttribute("red", Integer.toString(box.getColor().getRed()));
        color.setAttribute("green", Integer.toString(box.getColor().getGreen()));
        color.setAttribute("blue", Integer.toString(box.getColor().getBlue()));
        root.appendChild(color);
        
        return xmlDoc;
    }

    private void writeXmlDomTreeToFile(Document xmlDoc, String filename) throws TransformerConfigurationException, TransformerException {
        // Source
        Source srcXml = new DOMSource(xmlDoc);
        // Result
        Result dstFile = new StreamResult(filename);
        // Create Transormator
        Transformer transformer = TransformerFactory.newInstance().newTransformer(srcXml);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(srcXml, dstFile);
    }
    
}
