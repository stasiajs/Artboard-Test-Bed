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
       selections = selectedXShape.getSelections();
      }
     }
    }

    //select
    //    if (mode == Config.SELECT_MODE) {
    //     selectedXShape = null;
    //     for (int i = 0; i < shapeList.size(); i++) {
    //
    //      if (shapeList.get(i).getShape() instanceof Line2D.Double) {
    //       if (shapeList.get(i).getShape().intersects(e.getX() - 1, e.getY() - 1, 3, 3)) {
    //        System.out.println("ololl");
    //        selectedXShape = shapeList.get(i);
    //
    //       }
    //      }
    //      else {
    //       if (shapeList.get(i).getShape().contains(e.getX(), e.getY())) {
    //        System.out.println("Hit position: " + i);
    //        selectedXShape = shapeList.get(i);
    //       }
    //      }
    //
    //      if (selectedXShape != null && !selectedXShape.equals(null)) {
    //       drawSelections();
    //      }
    //      else {
    //       selections = new Rectangle2D.Double[4];
    //      }
    //     }
    //    }

   }

   public void mouseReleased(MouseEvent e) {
    resize = false;
    drag = false;
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
    System.out.println("x perssed: " + pressX);
    System.out.println("y perssed: " + pressY);
   }
  });
  addMouseMotionListener(new MouseMotionListener() {

   public void mouseDragged(MouseEvent e) {
    //draw
    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      //      case Config.DRAW_LINE: {
      //       drawXShape = drawLine(pressX, pressY, e.getX(), e.getY());
      //       break;
      //      }
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

     }
    }

    else if (mode == Config.SELECT_MODE && selectedXShape != null && !selectedXShape.equals(null)) {

     int tempHitSelection = getHitSelection(e.getX(), e.getY());

     if (resize == false && tempHitSelection >= 0 && tempHitSelection < Config.NOT_HIT) {
      hitSelection = tempHitSelection;
      resize = true;
     }

     else if (resize == true) {
      selectedXShape.resize(e.getX(), e.getY(), hitSelection);
      selections = selectedXShape.getSelections();
     }

     else if (selectedXShape.isClicked(e.getX(), e.getY()) && drag == false) {
      drag = true;
     }

     else if (drag == true) {
      selectedXShape.dragTo(e.getX(), e.getY());
      selections = selectedXShape.getSelections();
     }

     //     if (selectedXShape.getShape() instanceof Rectangle2D.Double) {
     //      Rectangle2D.Double rect = (Rectangle2D.Double) selectedXShape.getShape();
     //
     //      if (selections[3].contains(e.getX(), e.getY()) && resize == false) {
     //       System.out.println("resize");
     //       resize = true;
     //      }
     //
     //      else if (resize == true) {
     //       rect.setRect(rect.getX(), rect.getY(), e.getX() - rect.getX(), e.getY() - rect.getY());
     //      }
     //      
     //      else if (rect.contains(e.getX(), e.getY()) && drag == false){
     //       drag = true;
     //      }
     //
     //      else if (drag == true) {
     //       rect.setRect(e.getX() - rect.getWidth() / 2, e.getY() - rect.getHeight() / 2, rect.getWidth(), rect.getHeight());
     //      }
     //
     //      // selection 
     //      drawSelections();
     //     }
     //
     //     else if (selectedXShape.getShape() instanceof Line2D.Double) {
     //      Line2D.Double line = (Line2D.Double) selectedXShape.getShape();
     //
     //      if (selections[3].contains(e.getX(), e.getY()) && resize == false) {
     //       System.out.println("resize");
     //       resize = true;
     //      }
     //
     //      else if (resize == true) {
     //       line.setLine(line.getX1(), line.getY1(), e.getX(), e.getY());
     //      }
     //      // ELSE IF drag == true usw
     //      else {
     //       int diffX = (int) (line.getX2()-line.getX1());
     //       int diffY = (int) (line.getY2()-line.getY1());
     //       line.setLine(e.getX()-(diffX+1)/2, e.getY()-(diffX+1)/2, e.getX()+diffX/2, e.getY()+diffY/2);
     //      }
     //      drawSelections();
     //     }

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
   if (c != null && c.getColor() != null && c.getShape() != null) {
    g.setColor(c.getColor());
    g.draw(c.getShape());
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

 private Line2D.Double drawLine(int x1, int y1, int x2, int y2) {
  return new Line2D.Double(x1, y1, x2, y2);
 }

 // private Rectangle2D.Double drawRect(int x1, int y1, int x2, int y2) {
 //  if (x2 < x1 && y2 < y1) {
 //   return new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2);
 //  }
 //  else if (x2 < x1 && y2 >= y1) {
 //   return new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1);
 //  }
 //  else if (x2 >= x1 && y2 < y1) {
 //   return new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2);
 //  }
 //  else {
 //   return new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1);
 //  }
 // }

 // private void drawSelections() {
 //  selections[0] = new Rectangle2D.Double(selectedXShape.getShape().getBounds().getX() - 3,
 //    selectedXShape.getShape().getBounds().getY() - 3, 6, 6);
 //  selections[1] = new Rectangle2D.Double(selectedXShape.getShape().getBounds().getMaxX() - 3,
 //    selectedXShape.getShape().getBounds().getY() - 3, 6, 6);
 //  selections[2] = new Rectangle2D.Double(selectedXShape.getShape().getBounds().getX() - 3,
 //    selectedXShape.getShape().getBounds().getMaxY() - 3, 6, 6);
 //  selections[3] = new Rectangle2D.Double(selectedXShape.getShape().getBounds().getMaxX() - 3,
 //    selectedXShape.getShape().getBounds().getMaxY() - 3, 6, 6);
 // }

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

}
