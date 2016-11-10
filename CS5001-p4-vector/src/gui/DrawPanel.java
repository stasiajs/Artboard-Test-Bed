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
 * The Class DrawPanel draws all the shapes of the shape list on a JPanel. It is also responsible for displaying changes regarding resizing and moving.
 */
public class DrawPanel extends JPanel {

 /** The model with the underlying shapes. */
 private Model model;

 /** X position, when mouse is pressed. */
 private int pressX = 0;

 /** Y position, when mouse is pressed. */
 private int pressY = 0;

 /** Temporary reference to the XShape that is being drawn. */
 private XShape drawXShape;

 /** Temporary reference to the XShape that is selected. */
 private XShape selectedXShape;

 /** The current drawing color. */
 private Color color;

 /** The current drawing mode of shapes. Drawing lines is default. */
 private int shapeMode = Config.DRAW_LINE;

 /** Defines the current mode. Is either DRAW_MODE or SELECT_MODE. DRAW_MODE is default. */
 private int mode = Config.DRAW_MODE;

 /** Defines if the shapes are being filled or not. False is default. */
 private boolean setFill = false;

 /** Turns true, when a shape is being resized. */
 private boolean resize = false;

 /** Turns true, when a shape is being dragged.*/
 private boolean drag = false;

 /** Turns true, when a shape is being exported and makes the resizeBoxes invisible for the exported image. */
 private boolean export = false;

 /** The number of the resize box that is hit when resizing the selected shape. NOT_HIT (4) is default. */
 private int hitResizedBox = Config.NOT_HIT;

 /** The reference of the shape list in the model. */
 private ArrayList<XShape> shapeList;

 /** The resize boxes that appear when selecting a shape. */
 private Rectangle2D.Double[] resizeBoxes;

 /**
  * Instantiates a new draw panel.
  *
  * @param model the model with the list of shapes
  */
 public DrawPanel(Model model) {

  this.model = model;
  shapeList = model.getShapeList();
  resizeBoxes = new Rectangle2D.Double[Config.BOX_INT];

  this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
  setBackground(Color.WHITE);
  setVisible(true);
  color = Color.BLACK;

  addMouseListener(new MouseListener() {

   @Override
   public void mouseClicked(MouseEvent e) {

    selectedXShape = null;
    // when mouse is clicked in select mode, go through the list of shapes and get a shape if one was hit
    if (mode == Config.SELECT_MODE) {
     for (int i = 0; i < shapeList.size(); i++) {
      if (shapeList.get(i).isClicked(e.getX(), e.getY())) {
       selectedXShape = shapeList.get(i);
      }
     }
     // show the resize boxes of the selected shape
     if ((selectedXShape != null) && !selectedXShape.equals(null)) {
      resizeBoxes = getSelections(selectedXShape);
     }
    }
   }

   @Override
   public void mouseReleased(MouseEvent e) {

    // stop dragging when released
    drag = false;
    // stop resizing when released
    resize = false;
    hitResizedBox = Config.NOT_HIT;

    // reset selection boxes for the selected element as soon as the mouse was released
    if (mode == Config.SELECT_MODE) {
     if ((selectedXShape != null) && !selectedXShape.equals(null)) {
      selectedXShape.updateBounds();
      resizeBoxes = getSelections(selectedXShape);
     }
    }

    // if in draw mode and a new shape was drawn, add it to the list
    else if (mode == Config.DRAW_MODE) {
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
    // save the first x and y position as soon as the mouse is pressed
    pressX = e.getX();
    pressY = e.getY();
   }
  });

  addMouseMotionListener(new MouseMotionListener() {

   @Override
   public void mouseDragged(MouseEvent e) {

    // preview of the shape that is being drawn when in draw mode
    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE:
       drawXShape = new XLine();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_RECT:
       drawXShape = new XRect();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_SQUARE:
       drawXShape = new XSquare();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_CIRCLE:
       drawXShape = new XCircle();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_ELLIPSE:
       drawXShape = new XEllipse();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_HEX:
       drawXShape = new XHexagon();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.draw(pressX, pressY, e.getX(), e.getY());
       break;

      default:
       break;

     }
    }

    // in select mode check if one of the resize boxes was hit and resize accordingly
    // if not check if the mouse was dragged on the element and drag it accordingly
    else if ((mode == Config.SELECT_MODE) && (selectedXShape != null) && !selectedXShape.equals(null)) {

     int tempHitSelection = getResizeBoxNumber(e.getX(), e.getY());

     // if one of the boxes was dragged, start the resize mode
     if (!resize && (tempHitSelection >= 0) && (tempHitSelection < Config.NOT_HIT)) {
      model.addUndoAction();
      hitResizedBox = tempHitSelection;
      resize = true;
     }

     // execute the resize mode as long as the mouse is pressed
     else if (resize) {
      resizeBoxes = getSelections(selectedXShape);
      selectedXShape.resize(e.getX(), e.getY(), hitResizedBox);
     }

     // check if the shape was clicked and initiate the drag mode if so
     else if (selectedXShape.isClicked(e.getX(), e.getY()) && !drag) {
      model.addUndoAction();
      drag = true;
     }

     // execute the drag mode as long as the mouse is clicked
     else if (drag) {
      selectedXShape.dragTo(e.getX(), e.getY());
      resizeBoxes = getSelections(selectedXShape);
     }
    }

    repaint();
   }

   @Override
   public void mouseMoved(MouseEvent e) {
   }
  });

 }

 /**
  * The paintComponent paints all the shapes on the panel. First all the shapes from the list are painted.
  * Then if a new shape is being drawn, it gets painted.
  * At last the selection boxes are added, if there is a selected element and if the image is not being exported to PNG at the moment.
  *
  * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
  */
 @Override
 public void paintComponent(Graphics graphics) {
  Graphics2D g = (Graphics2D) graphics;
  super.paintComponent(g);

  // paint the shapes from the list
  for (int i = 0; i < model.getShapeList().size(); i++) {
   XShape xshape = model.getShapeList().get(i);

   // use the drawImage() method if the shape is an XImage
   if ((xshape instanceof XImage) && (xshape != null) && !xshape.equals(null) && (xshape.getShape() != null)) {
    XImage ximage = ((XImage) xshape);
    g.drawImage(ximage.getImage(), (int) ximage.getShape().getBounds().getMinX(),
      (int) ximage.getShape().getBounds().getMinY(), (int) ximage.getShape().getBounds().getWidth(),
      (int) ximage.getShape().getBounds().getHeight(), null);
   }

   // else draw normally
   else {
    if ((xshape != null) && (xshape.getColor() != null) && (xshape.getShape() != null)) {
     g.setColor(xshape.getColor());
     g.draw(xshape.getShape());
     if (xshape.isFill()) {
      g.fill(xshape.getShape());
     }
    }
   }
  }

  // paint the current drawXShape
  if ((drawXShape != null) && !drawXShape.equals(null)) {
   g.setColor(color);
   if (drawXShape.isFill()) {
    g.fill(drawXShape.getShape());
   }
   g.draw(drawXShape.getShape());
  }

  // add the selection marks if applicable
  if (!export && (resizeBoxes != null) && !resizeBoxes.equals(null) && (selectedXShape != null)
    && !selectedXShape.equals(null)) {
   for (int i = 0; i < resizeBoxes.length; i++) {
    if ((resizeBoxes[i] != null) && !resizeBoxes[i].equals(null)) {
     g.setColor(Color.BLACK);
     g.draw(resizeBoxes[i]);

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
  * Gets the selected XShape.
  *
  * @return the selected XShape
  */
 public XShape getSelectedXShape() {
  return selectedXShape;
 }

 /**
  * Sets the selected XShape.
  *
  * @param selectedXShape the new selected XShape
  */
 public void setSelectedXShape(XShape selectedXShape) {
  this.selectedXShape = selectedXShape;
 }

 /**
  * Gets the number of the resize box that was clicked.
  *
  * @param x the x coordinate of the mouse click
  * @param y the y coordinate of the mouse click
  * @return the array of resized boxes
  */
 private int getResizeBoxNumber(int x, int y) {
  if ((resizeBoxes != null) && !resizeBoxes.equals(null)) {
   for (int i = 0; i < resizeBoxes.length; i++) {
    if ((resizeBoxes[i] != null) && !resizeBoxes[i].equals(null)) {
     if (resizeBoxes[i].contains(x, y)) {
      return i;
     }
    }
   }
  }
  return Config.NOT_HIT;
 }

 /**
  * Gets an array of the resize boxes.
  *
  * @param xshape the selected shape that needs resize boxes
  * @return an array of resize boxes
  */
 public Rectangle2D.Double[] getSelections(XShape xshape) {

  Rectangle2D.Double[] selections = new Rectangle2D.Double[Config.BOX_INT];
  if ((xshape != null) && !xshape.equals(null)) {

   selections[Config.HIT_TOP_LEFT] = new Rectangle2D.Double(xshape.getShape().getBounds().getMinX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMinY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);
   selections[Config.HIT_TOP_RIGHT] = new Rectangle2D.Double(xshape.getShape().getBounds().getMaxX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMinY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);
   selections[Config.HIT_BOTTOM_LEFT] = new Rectangle2D.Double(
     xshape.getShape().getBounds().getMinX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMaxY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);
   selections[Config.HIT_BOTTOM_RIGHT] = new Rectangle2D.Double(
     xshape.getShape().getBounds().getMaxX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMaxY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);

   return selections;
  }

  else {
   return null;
  }

 }

 /**
  * Sets the shape list reference.
  *
  * @param shapeList the new shape list
  */
 public void setShapeList(ArrayList<XShape> shapeList) {
  this.shapeList = shapeList;
 }

 /**
  * Checks if shapes are being filled when drawn.
  *
  * @return true, if is sets the fill
  */
 public boolean isSetFill() {
  return setFill;
 }

 /**
  * Sets if shapes shall be filled when drawn.
  *
  * @param setFill the new sets the fill
  */
 public void setSetFill(boolean setFill) {
  this.setFill = setFill;
 }

 /**
  * Checks if the shapes are being exported to PNG.
  *
  * @return true, if is export
  */
 public boolean isExport() {
  return export;
 }

 /**
  * Sets the export mode to false or true.
  *
  * @param export the export mode
  */
 public void setExport(boolean export) {
  this.export = export;
 }

}
