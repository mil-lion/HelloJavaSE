/*
 * File:    EntityFactory.java
 * Project: HelloJavaSE
 * Date:    31 окт. 2019 г. 23:03:58
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.applet;

import java.applet.Applet;
import java.awt.Graphics;

/*
<html>
  <head>
    <title>Hello from My Applet</title>
  </head>
  <body>
     <h1>Run Applet</h1>
     <applet archive="HelloWorld.jar" code="ru/test/applet/HelloApplet.class" width="400" height="200">
        <param name="toWho" value="Igor Morenko" />
        Can't Applet support this browser!
     </applet>
  </body>
</html>
*/

/**
 * Пример аплета
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloApplet extends Applet {

    // Инициализация аплета
    @Override
    public void init() {
        super.init();
        System.out.println("@@@@ init my applet!");
    }

    // Отрисовка содержимого окна аплета
    @Override
    public void paint(Graphics g) {
        System.out.println("@@@@ paint my applet!");
        g.drawString("Hello from My Applet to '" 
                + getParameter("toWho")         // имя берем из параметров аплета
                + "'!", 10, 10);
        g.drawLine(100, 50, 150, 100);
    }

    // Уничтожение аплета
    @Override
    public void destroy() {
        super.destroy(); 
        System.out.println("@@@@ destroy my applet!");
    }
}

