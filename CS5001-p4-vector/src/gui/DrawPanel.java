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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Model;
import shapes.CShape;

public class DrawPanel extends JPanel {

 private Model model;

 private int pressX = 0;
 private int pressY = 0;

 private Shape shape;
 private Shape dragShape;
 private Color color;
 private int shapeMode = Config.DRAW_LINE;
 private int mode = Config.DRAW_MODE;

 private boolean setFill = false;

 ArrayList<CShape> shapeList;

 public DrawPanel(Model model) {

  this.model = model;
  this.shapeList = model.getShapeList();

  this.setSize(800, 600);
  this.setBackground(Color.WHITE);
  this.setVisible(true);

  color = Color.BLACK;

  addMouseListener(new MouseListener() {

   public void mouseClicked(MouseEvent e) {
    pressX = e.getX();
    pressY = e.getY();
    System.out.println("Mouse clicked " + e.getX() + " " + e.getY());

    for (int i = 0; i < shapeList.size(); i++) {
     if (shapeList.get(i).getShape().contains(e.getX(), e.getY())) {
      System.out.println("Hit position: " + i);
     }

     if (shapeList.get(i).getShape().intersects(e.getX() - 1, e.getY() - 1, e.getX() - 1, e.getY() + 1)) {
      System.out.println("Intersect position: " + i);
     }
    }

   }

   public void mouseReleased(MouseEvent e) {
    System.out.println("Mouse released " + e.getX() + " " + e.getY());

    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE: {
       shape = drawLine(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_RECT: {
       System.out.println("rect");
       shape = drawRect(pressX, pressY, e.getX(), e.getY());
       break;
      }

     }
     model.addShape(new CShape(shape, color));
     shape = null;
    }
    repaint();

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
    System.out.println("Mouse pressed " + e.getX() + " " + e.getY());
    pressX = e.getX();
    pressY = e.getY();
   }
  });
  addMouseMotionListener(new MouseMotionListener() {
   public void mouseDragged(MouseEvent e) {
    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE: {
       shape = drawLine(pressX, pressY, e.getX(), e.getY());
       break;
      }
      case Config.DRAW_RECT: {
       System.out.println("rectDrag");
       shape = drawRect(pressX, pressY, e.getX(), e.getY());
       break;
      }

     }
    }

    else if (mode == Config.SELECT_MODE) {
     //     int lastShape=-1;
     for (int i = 0; i < shapeList.size(); i++) {
      if (shapeList.get(i).getShape().contains(e.getX(), e.getY())) {
       System.out.println("Hit position: " + i);
       //       lastShape = i;
       dragShape = shapeList.get(i).getShape();
      }
     }
     //     if (lastShape >= 0){
     //      
     //     }
     if (dragShape instanceof Rectangle2D.Double) {
      Rectangle2D.Double rect = (Rectangle2D.Double) dragShape;
      rect.setRect(e.getX() - rect.getWidth() / 2, e.getY() - rect.getHeight() / 2, rect.getWidth(), rect.getHeight());
     }

    }

    repaint();
   }

   public void mouseMoved(MouseEvent e) {

   }
  });

 }

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

 }

 Color getColor() {
  return color;
 }

 void setColor(Color color) {
  this.color = color;
 }

 public void setDrawMode(int drawMode) {
  this.shapeMode = drawMode;
 }

 public void setMode(int mode) {
  this.mode = mode;
 }

 private Line2D.Double drawLine(int x1, int y1, int x2, int y2) {
  return new Line2D.Double(x1, y1, x2, y2);
 }

 private Rectangle2D.Double drawRect(int x1, int y1, int x2, int y2) {
  if (x2 < x1 && y2 < y1) {
   return new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2);
  }
  else if (x2 < x1 && y2 >= y1) {
   return new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1);
  }
  else if (x2 >= x1 && y2 < y1) {
   return new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2);
  }
  else {
   return new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1);
  }
 }

}
