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

 private Shape drawShape;
 private CShape selectedCShape;

 private Color color;
 private int shapeMode = Config.DRAW_LINE;
 private int mode = Config.DRAW_MODE;

 private boolean setFill = false;
 private boolean resize = false;

 ArrayList<CShape> shapeList;
 Rectangle2D.Double[] selections;

 public DrawPanel(Model model) {

  this.model = model;
  this.shapeList = model.getShapeList();
  selections = new Rectangle2D.Double[4];

  this.setSize(800, 600);
  this.setBackground(Color.WHITE);
  this.setVisible(true);

  color = Color.BLACK;

  addMouseListener(new MouseListener() {

   public void mouseClicked(MouseEvent e) {
    pressX = e.getX();
    pressY = e.getY();
    System.out.println("Mouse clicked " + e.getX() + " " + e.getY());

    if (mode == Config.SELECT_MODE) {
     selectedCShape = null;
     for (int i = 0; i < shapeList.size(); i++) {

      if (shapeList.get(i).getShape() instanceof Line2D.Double) {
       if (shapeList.get(i).getShape().intersects(e.getX() - 1, e.getY() - 1, 3, 3)) {
        System.out.println("ololl");
        selectedCShape = shapeList.get(i);

       }
      }
      else {
       if (shapeList.get(i).getShape().contains(e.getX(), e.getY())) {
        System.out.println("Hit position: " + i);
        selectedCShape = shapeList.get(i);
       }
      }

      if (selectedCShape != null && !selectedCShape.equals(null)) {
       drawSelections();
      }
      else {
       selections = new Rectangle2D.Double[4];
      }

      //     if (shapeList.get(i).getShape().intersects(e.getX() - 1, e.getY() - 1, e.getX() - 1, e.getY() + 1)) {
      //      System.out.println("Intersect position: " + i);
      //     }
     }
    }

   }

   public void mouseReleased(MouseEvent e) {
    System.out.println("Mouse released " + e.getX() + " " + e.getY());
    resize = false;

    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE: {
       drawShape = drawLine(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_RECT: {
       System.out.println("rect");
       drawShape = drawRect(pressX, pressY, e.getX(), e.getY());
       break;
      }

     }
     model.addShape(new CShape(drawShape, color));
     drawShape = null;
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
       drawShape = drawLine(pressX, pressY, e.getX(), e.getY());
       break;
      }
      case Config.DRAW_RECT: {
       drawShape = drawRect(pressX, pressY, e.getX(), e.getY());
       break;
      }

     }
    }

    else if (mode == Config.SELECT_MODE && selectedCShape != null && !selectedCShape.equals(null)) {

     if (selectedCShape.getShape() instanceof Rectangle2D.Double) {
      Rectangle2D.Double rect = (Rectangle2D.Double) selectedCShape.getShape();

      if (selections[3].contains(e.getX(), e.getY()) && resize == false) {
       System.out.println("pups");
       resize = true;
      }

      if (resize == true) {
       rect.setRect(rect.getX(), rect.getY(), e.getX() - rect.getX(), e.getY() - rect.getY());
      }

      else {
       rect.setRect(e.getX() - rect.getWidth() / 2, e.getY() - rect.getHeight() / 2, rect.getWidth(), rect.getHeight());
      }

      // selection 
      drawSelections();
     }

     else if (selectedCShape.getShape() instanceof Line2D.Double) {
      Line2D.Double line = (Line2D.Double) selectedCShape.getShape();

      if (selections[3].contains(e.getX(), e.getY()) && resize == false) {
       System.out.println("pups");
       resize = true;
      }

      if (resize == true) {
       line.setLine(line.getX1(), line.getY1(), e.getX(), e.getY());
      }
      
      else {
       int diffX = (int) (line.getX2()-line.getX1());
       int diffY = (int) (line.getY2()-line.getY1());
       line.setLine(e.getX()-(diffX+1)/2, e.getY()-(diffX+1)/2, e.getX()+diffX/2, e.getY()+diffY/2);
      }
      drawSelections();
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

  // paint the shapes
  for (int i = 0; i < model.getShapeList().size(); i++) {
   CShape c = model.getShapeList().get(i);
   if (c != null && c.getColor() != null && c.getShape() != null && c.isVisible() == true) {
    g.setColor(c.getColor());
    g.draw(c.getShape());
   }
  }

  // paint the current drawShape
  if (drawShape != null && !drawShape.equals(null)) {
   g.setColor(color);
   g.draw(drawShape);
  }

  // add the selection marks
  if (selections != null && !selections.equals(null) && selectedCShape != null && !selectedCShape.equals(null)) {
   for (int i = 0; i < selections.length; i++) {
    if (selections[i] != null && !selections[i].equals(null)) {
     g.setColor(Color.BLACK);
     g.draw(selections[i]);
    }
   }
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

 public CShape getSelectedCShape() {
  return selectedCShape;
 }

 public void setSelectedCShape(CShape selectedCShape) {
  this.selectedCShape = selectedCShape;
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

 private void drawSelections() {
  selections[0] = new Rectangle2D.Double(selectedCShape.getShape().getBounds().getX() - 3,
    selectedCShape.getShape().getBounds().getY() - 3, 6, 6);
  selections[1] = new Rectangle2D.Double(selectedCShape.getShape().getBounds().getMaxX() - 3,
    selectedCShape.getShape().getBounds().getY() - 3, 6, 6);
  selections[2] = new Rectangle2D.Double(selectedCShape.getShape().getBounds().getX() - 3,
    selectedCShape.getShape().getBounds().getMaxY() - 3, 6, 6);
  selections[3] = new Rectangle2D.Double(selectedCShape.getShape().getBounds().getMaxX() - 3,
    selectedCShape.getShape().getBounds().getMaxY() - 3, 6, 6);
 }

}
