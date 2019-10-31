/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.test.applet;

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
 *
 * @author Администратор
 */
public class HelloApplet extends Applet {

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("destroy my applet!");
    }

    @Override
    public void init() {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("init my applet!");
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.drawString("Hello from My Applet to '" + getParameter("toWho") + "'!", 10, 10);
        g.drawLine(100, 50, 150, 100);
    }
}
