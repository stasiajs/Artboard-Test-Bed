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

import com.sun.prism.BasicStroke;

import model.Model;
import shapes.XCircle;
import shapes.XEllipse;
import shapes.XHexagon;
import shapes.XImage;
import shapes.XLine;
import shapes.XRect;
import shapes.XShape;
import shapes.XSquare;

public class DrawPanel extends JPanel {

 private Model model;

 private int pressX = 0;
 private int pressY = 0;

 private XShape drawXShape;
 private XShape selectedXShape;

 private Color color;
 private int shapeMode = Config.DRAW_LINE;
 private int mode = Config.DRAW_MODE;

 private boolean setFill = false;
 private boolean resize = false;
 private boolean drag = false;
 private int hitSelection = 5;

 ArrayList<XShape> shapeList;
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

    selectedXShape = null;

    if (mode == Config.SELECT_MODE) {
     for (int i = 0; i < shapeList.size(); i++) {
      if (shapeList.get(i).isClicked(e.getX(), e.getY())) {
       System.out.println(i + " was clicked");
       selectedXShape = shapeList.get(i);

      }
     }
     if (selectedXShape != null && !selectedXShape.equals(null)) {
      selections = getSelections(selectedXShape);
     }
     //     else {
     //      selections = new Rectangle2D.Double[4];
     //     }

    }

   }

   public void mouseReleased(MouseEvent e) {

    if (mode == Config.SELECT_MODE) {
     if (selectedXShape != null && !selectedXShape.equals(null)) {
      selectedXShape.updateBounds();
      selections = getSelections(selectedXShape);

     }
    }

    if (drag == true) {
     drag = false;
    }

    if (resize == true) {
     resize = false;
    }

    hitSelection = 5;

    if (mode == Config.DRAW_MODE) {

     if (drawXShape != null && !drawXShape.equals(null)) {
      model.addShape(drawXShape);
     }
     drawXShape = null;
    }

    repaint();

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
    pressX = e.getX();
    pressY = e.getY();
   }
  });
  addMouseMotionListener(new MouseMotionListener() {

   public void mouseDragged(MouseEvent e) {
    //drawPreview
    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE: {
       drawXShape = new XLine();
       drawXShape.setColor(color);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }
      case Config.DRAW_RECT: {
       drawXShape = new XRect();
       drawXShape.setColor(color);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_SQUARE: {
       drawXShape = new XSquare();
       drawXShape.setColor(color);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_CIRCLE: {
       drawXShape = new XCircle();
       drawXShape.setColor(color);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_ELLIPSE: {
       drawXShape = new XEllipse();
       drawXShape.setColor(color);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_HEX: {
       drawXShape = new XHexagon();
       drawXShape.setColor(color);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

     }
    }

    else if (mode == Config.SELECT_MODE && selectedXShape != null && !selectedXShape.equals(null)) {

     int tempHitSelection = getHitSelection(e.getX(), e.getY());
    

     if (resize == false && tempHitSelection >= 0 && tempHitSelection < Config.NOT_HIT) {
      model.addUndoAction();
      hitSelection = tempHitSelection;
      System.out.println("Hit selection box: " +hitSelection);
      resize = true;
     }

     else if (resize == true) {
      selections = getSelections(selectedXShape);
      selectedXShape.resize(e.getX(), e.getY(), hitSelection);
      
     }

     else if (selectedXShape.isClicked(e.getX(), e.getY()) && drag == false) {
      model.addUndoAction();
      drag = true;
     }

     else if (drag == true) {

      selectedXShape.dragTo(e.getX(), e.getY());
      selections = getSelections(selectedXShape);
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
   XShape c = model.getShapeList().get(i);

   if (c instanceof XImage && c != null && !c.equals(null) && c.getShape() != null) {

    XImage ximage = ((XImage) c);

    g.drawImage(ximage.getImage(), ximage.getX(), ximage.getY(), null);

   }
   else {
    if (c != null && c.getColor() != null && c.getShape() != null) {
     g.setColor(c.getColor());
     //     g.setPaint(c.getColor());
     //     g.setStroke(s);
     //     g.setStroke());
     g.draw(c.getShape());
     //     g.drawPolygon(x);
    }
   }

  }

  // paint the current drawXShape
  if (drawXShape != null && !drawXShape.equals(null)) {
   g.setColor(color);
   g.draw(drawXShape.getShape());
  }

  // add the selection marks
  if (selections != null && !selections.equals(null) && selectedXShape != null && !selectedXShape.equals(null)) {
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

 public XShape getSelectedCShape() {
  return selectedXShape;
 }

 public void setSelectedCShape(XShape selectedCShape) {
  this.selectedXShape = selectedCShape;
 }

 private int getHitSelection(int x, int y) {
  if (selections != null && !selections.equals(null)) {
   for (int i = 0; i < selections.length; i++) {
    if (selections[i] != null && !selections[i].equals(null)) {
     if (selections[i].contains(x, y)) {
      return i;
     }
    }
   }
  }
  return Config.NOT_HIT;
 }

 public Rectangle2D.Double[] getSelections(XShape shape) {

  Rectangle2D.Double[] selections = new Rectangle2D.Double[4];
  if (shape != null && !shape.equals(null)) {
   selections[0] = new Rectangle2D.Double(shape.getShape().getBounds().getMinX() - 3,
     shape.getShape().getBounds().getMinY() - 3, 6, 6);
   selections[1] = new Rectangle2D.Double(shape.getShape().getBounds().getMaxX() - 3,
     shape.getShape().getBounds().getMinY() - 3, 6, 6);
   selections[2] = new Rectangle2D.Double(shape.getShape().getBounds().getMinX() - 3,
     shape.getShape().getBounds().getMaxY() - 3, 6, 6);
   selections[3] = new Rectangle2D.Double(shape.getShape().getBounds().getMaxX() - 3,
     shape.getShape().getBounds().getMaxY() - 3, 6, 6);

   return selections;
  }

  else {
   return null;
  }

 }

 public ArrayList<XShape> getShapeList() {
  return shapeList;
 }

 public void setShapeList(ArrayList<XShape> shapeList) {
  this.shapeList = shapeList;
 }

}
