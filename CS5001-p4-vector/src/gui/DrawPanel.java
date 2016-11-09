package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.Model;
import shapes.XCircle;
import shapes.XEllipse;
import shapes.XHexagon;
import shapes.XImage;
import shapes.XLine;
import shapes.XRect;
import shapes.XShape;
import shapes.XSquare;

/**
 * The Class DrawPanel.
 */
public class DrawPanel extends JPanel {

 /** The model. */
 private Model model;

 /** The press X. */
 private int pressX = 0;

 /** The press Y. */
 private int pressY = 0;

 /** The draw X shape. */
 private XShape drawXShape;

 /** The selected X shape. */
 private XShape selectedXShape;

 /** The color. */
 private Color color;

 /** The shape mode. */
 private int shapeMode = Config.DRAW_LINE;

 /** The mode. */
 private int mode = Config.DRAW_MODE;

 /** The set fill. */
 private boolean setFill = false;

 /** The resize. */
 private boolean resize = false;

 /** The drag. */
 private boolean drag = false;

 /** The hit selection. */
 private int hitSelection = 5;

 /** The shape list. */
 ArrayList<XShape> shapeList;

 /** The selections. */
 Rectangle2D.Double[] selections;

 /**
  * Instantiates a new draw panel.
  *
  * @param model the model
  */
 public DrawPanel(Model model) {

  this.model = model;
  shapeList = model.getShapeList();
  selections = new Rectangle2D.Double[4];

  this.setSize(800, 600);
  setBackground(Color.WHITE);
  setVisible(true);

  color = Color.BLACK;

  addMouseListener(new MouseListener() {

   @Override
   public void mouseClicked(MouseEvent e) {

    selectedXShape = null;

    if (mode == Config.SELECT_MODE) {
     for (int i = 0; i < shapeList.size(); i++) {
      if (shapeList.get(i).isClicked(e.getX(), e.getY())) {
       selectedXShape = shapeList.get(i);

      }
     }
     if ((selectedXShape != null) && !selectedXShape.equals(null)) {
      selections = getSelections(selectedXShape);
     }
     //     else {
     //      selections = new Rectangle2D.Double[4];
     //     }

    }

   }

   @Override
   public void mouseReleased(MouseEvent e) {

    if (mode == Config.SELECT_MODE) {
     if ((selectedXShape != null) && !selectedXShape.equals(null)) {
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

     if ((drawXShape != null) && !drawXShape.equals(null)) {
      model.addShape(drawXShape);
     }
     drawXShape = null;
    }

    repaint();

   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
    pressX = e.getX();
    pressY = e.getY();
   }
  });
  addMouseMotionListener(new MouseMotionListener() {

   @Override
   public void mouseDragged(MouseEvent e) {
    //drawPreview
    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE: {
       drawXShape = new XLine();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }
      case Config.DRAW_RECT: {
       drawXShape = new XRect();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_SQUARE: {
       drawXShape = new XSquare();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_CIRCLE: {
       drawXShape = new XCircle();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_ELLIPSE: {
       drawXShape = new XEllipse();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }

      case Config.DRAW_HEX: {
       drawXShape = new XHexagon();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;
      }
      
     }
    }

    else if ((mode == Config.SELECT_MODE) && (selectedXShape != null) && !selectedXShape.equals(null)) {

     int tempHitSelection = getHitSelection(e.getX(), e.getY());

     if ((resize == false) && (tempHitSelection >= 0) && (tempHitSelection < Config.NOT_HIT)) {
      model.addUndoAction();
      hitSelection = tempHitSelection;
      resize = true;
     }

     else if (resize == true) {
      selections = getSelections(selectedXShape);
      selectedXShape.resize(e.getX(), e.getY(), hitSelection);

     }

     else if (selectedXShape.isClicked(e.getX(), e.getY()) && (drag == false)) {
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

   @Override
   public void mouseMoved(MouseEvent e) {

   }
  });

 }

 /* (non-Javadoc)
  * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
  */
 @Override
 public void paintComponent(Graphics graphics) {
  Graphics2D g = (Graphics2D) graphics;
  super.paintComponent(g);

  // paint the shapes
  for (int i = 0; i < model.getShapeList().size(); i++) {
   XShape xshape = model.getShapeList().get(i);

   if ((xshape instanceof XImage) && (xshape != null) && !xshape.equals(null) && (xshape.getShape() != null)) {
    XImage ximage = ((XImage) xshape);
    g.drawImage(ximage.getImage(), ximage.getX1(), ximage.getY1(), ximage.getWidth(), ximage.getHeight(), null);
   }
   else {
    if ((xshape != null) && (xshape.getColor() != null) && (xshape.getShape() != null)) {
     g.setColor(xshape.getColor());
     g.draw(xshape.getShape());
     if (xshape.isFill()){
      g.fill(xshape.getShape());
     }
    }
   }
  }

  // paint the current drawXShape
  if ((drawXShape != null) && !drawXShape.equals(null)) {
   g.setColor(color);
   if (drawXShape.isFill()){
    g.fill(drawXShape.getShape());
   }
   g.draw(drawXShape.getShape());
  }

  // add the selection marks
  if ((selections != null) && !selections.equals(null) && (selectedXShape != null) && !selectedXShape.equals(null)) {
   for (int i = 0; i < selections.length; i++) {
    if ((selections[i] != null) && !selections[i].equals(null)) {
     g.setColor(Color.BLACK);
     g.draw(selections[i]);
     
    }
   }
  }
 }

 /**
  * Gets the color.
  *
  * @return the color
  */
 Color getColor() {
  return color;
 }

 /**
  * Sets the color.
  *
  * @param color the new color
  */
 void setColor(Color color) {
  this.color = color;
 }

 /**
  * Sets the draw mode.
  *
  * @param drawMode the new draw mode
  */
 public void setDrawMode(int drawMode) {
  shapeMode = drawMode;
 }

 /**
  * Sets the mode.
  *
  * @param mode the new mode
  */
 public void setMode(int mode) {
  this.mode = mode;
 }

 /**
  * Gets the selected C shape.
  *
  * @return the selected C shape
  */
 public XShape getSelectedXShape() {
  return selectedXShape;
 }

 /**
  * Sets the selected C shape.
  *
  * @param selectedXShape the new selected C shape
  */
 public void setSelectedXShape(XShape selectedXShape) {
  this.selectedXShape = selectedXShape;
 }

 /**
  * Gets the hit selection.
  *
  * @param x the x
  * @param y the y
  * @return the hit selection
  */
 private int getHitSelection(int x, int y) {
  if ((selections != null) && !selections.equals(null)) {
   for (int i = 0; i < selections.length; i++) {
    if ((selections[i] != null) && !selections[i].equals(null)) {
     if (selections[i].contains(x, y)) {
      return i;
     }
    }
   }
  }
  return Config.NOT_HIT;
 }

 /**
  * Gets the selections.
  *
  * @param xshape the shape
  * @return the selections
  */
 public Rectangle2D.Double[] getSelections(XShape xshape) {

  Rectangle2D.Double[] selections = new Rectangle2D.Double[4];
  if ((xshape != null) && !xshape.equals(null)) {
   selections[0] = new Rectangle2D.Double(xshape.getShape().getBounds().getMinX() - 3,
     xshape.getShape().getBounds().getMinY() - 3, 6, 6);
   selections[1] = new Rectangle2D.Double(xshape.getShape().getBounds().getMaxX() - 3,
     xshape.getShape().getBounds().getMinY() - 3, 6, 6);
   selections[2] = new Rectangle2D.Double(xshape.getShape().getBounds().getMinX() - 3,
     xshape.getShape().getBounds().getMaxY() - 3, 6, 6);
   selections[3] = new Rectangle2D.Double(xshape.getShape().getBounds().getMaxX() - 3,
     xshape.getShape().getBounds().getMaxY() - 3, 6, 6);

   return selections;
  }

  else {
   return null;
  }

 }

 /**
  * Sets the shape list.
  *
  * @param shapeList the new shape list
  */
 public void setShapeList(ArrayList<XShape> shapeList) {
  this.shapeList = shapeList;
 }

 public boolean isSetFill() {
  return setFill;
 }

 public void setSetFill(boolean setFill) {
  this.setFill = setFill;
 }

}
