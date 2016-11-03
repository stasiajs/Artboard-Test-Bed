package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import model.Model;
import shapes.CShape;

public class DrawPanel extends JPanel {

 Model model;

 int clickX = 0;
 int clickY = 0;

 int releaseX = 0;
 int releaseY = 0;

 Shape shape;
 private Color color;

 // ArrayList<CShape> ShapeList;

 public DrawPanel(Model model) {

  this.model = model;
  //  this.ShapeList = model.getShapeList();

  this.setSize(800, 600);
  this.setBackground(Color.WHITE);
  this.setVisible(true);

  color = Color.BLACK;

  addMouseListener(new MouseListener() {

   public void mouseClicked(MouseEvent e) {
    clickX = e.getX();
    clickY = e.getY();
    System.out.println("Mouse clicked " + e.getX() + " " + e.getY());
   }

   public void mouseReleased(MouseEvent e) {
    //    paint(new Line2D.Double(clickX, clickY, e.getX(), e.getY()));
    //    draw
    System.out.println("Mouse released " + e.getX() + " " + e.getY());

    shape = new Line2D.Double(clickX, clickY, e.getX(), e.getY());
    model.addShape(new CShape(shape, color));
    shape = null;

    repaint();

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
    System.out.println("Mouse pressed " + e.getX() + " " + e.getY());
    clickX = e.getX();
    clickY = e.getY();
   }
  });
  addMouseMotionListener(new MouseMotionListener() {
   public void mouseDragged(MouseEvent e) {
    shape = new Line2D.Double(clickX, clickY, e.getX(), e.getY());

    repaint();
    //    System.out.println("Mouse dragged " + e.getX() + " " + e.getY());
   }

   public void mouseMoved(MouseEvent e) {
   }
  });

 }

 // public void paint(Graphics g) {
 //
 //  Graphics2D g2d = (Graphics2D) g;
 //
 //  //g2d.draw();
 //
 //  //Line2D line = new Line2D.Double (0, 0, 75, 75);
 //
 //  //  Graphics2D g2d = (Graphics2D) g;
 //  //  Line2D line = new Line2D.Double (0, 0, 75, 75);
 //  //  g2d.draw (line);
 //  //  Ellipse2D curve = new Ellipse2D.Double (10, 10, 20, 20);
 //  //  g2d.draw (curve);
 // }

 @Override
 public void paintComponent(Graphics graphics) {
  Graphics2D g = (Graphics2D) graphics;
  super.paintComponent(g);
  

  
  
  for (int i = 0; i < model.getShapeList().size(); i++) {
   CShape c = model.getShapeList().get(i);
   if (c != null && c.getColor() != null && c.getShape() != null && c.isVisible() == true) {
    g.setColor(c.getColor());
    g.draw(c.getShape());
   }
  }
  
  if (shape != null) {
   g.setColor(color);
   g.draw(shape);
  }
  
//  for (CShape c : model.getShapeList()) {
//   if (c != null && c.getColor() != null && c.getShape() != null && c.isVisible() == true) {
//    g.setColor(c.getColor());
//    g.draw(c.getShape());
//   }
//  }



 }
 
// public void repaint() {
//  super.repaint();
//  for (CShape c : model.getShapeList()) {
//   if (c != null && c.getColor() != null && c.getShape() != null && c.isVisible() == true) {
//    g.setColor(c.getColor());
//    g.draw(c.getShape());
//   }
//  }
// }

 Color getColor() {
  return color;
 }

 void setColor(Color color) {
  this.color = color;
 }

 // public void repaint() {
 //  
 // }
}
