package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;

import gui.Config;

/**
 * XShape describes an abstract eXtendedShape that holds an underlying shape, a color, the information if the shape shall be filled with color
 * and basic coordinates as well as height and width. Also the abstract XShape provide methods for drawing, resizing, dragging and click
 * recognition. XShapes are serializable as they need to be saved to file and for undo/redo purposes.
 */
public abstract class XShape implements Serializable {

 /** The underlying shape. */
 protected Shape shape;

 /** The color of the shape. */
 private Color color;

 /** Determines whether the shape is filled or not. Default is false. */
 private boolean fill = false;

 /** The x1 coordinate of the shape's frame. */
 protected int x1 = 0;

 /** The y1 coordinate of the shape's frame. */
 protected int y1 = 0;

 /** The x2 coordinate of the shape's frame. */
 protected int x2 = 0;

 /** The y2 coordinate of the shape's frame. */
 protected int y2 = 0;

 /** The width of the shape's frame.. */
 protected int width = 0;

 /** The height of the shape's frame. */
 protected int height = 0;

 /**
  * Checks if the shape was clicked on.
  *
  * @param x the x coordinate of the click
  * @param y the y coordinate of the click
  * @return true, if the shape clicked, false if not
  */
 public boolean isClicked(int x, int y) {
  return (shape.contains(x, y));
 }

 /**
  * Construct the shape.
  *
  * @param x1 the x1 coordinate of the shape's frame
  * @param y1 the y1 coordinate of the shape's frame
  * @param x2 the x2 coordinate of the shape's frame
  * @param y2 the y2 coordinate of the shape's frame
  */
 public abstract void construct(int x1, int y1, int x2, int y2);

 /**
  * Drag the shape to a specific position.
  *
  * @param x the x
  * @param y the y
  */
 public void dragTo(int x, int y) {
  construct(x - (width / 2), y - (height / 2), x + (width / 2), y + (height / 2));
 }

 /**
  * Resize.
  *
  * @param x the x
  * @param y the y
  * @param corner the corner
  */
 public void resize(int x, int y, int corner) {
  //  if (x2 == getShape().getBounds().getMaxX() && y2 == getShape().getBounds().getMaxY()) {
  if (corner == Config.HIT_BOTTOM_RIGHT) {
   construct(x1, y1, x, y);
  }
  else if (corner == Config.HIT_TOP_LEFT) {
   construct(x, y, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   construct(x1, y, x, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   construct(x, y1, x2, y);
  }
 }

 /**
  * Update the bounds. This needs to be done after resizing is finished, in order to update the coordinates of the resized shape.
  */
 public void updateBounds() {
  x1 = (int) shape.getBounds().getMinX();
  y1 = (int) shape.getBounds().getMinY();
  x2 = (int) shape.getBounds().getMaxX();
  y2 = (int) shape.getBounds().getMaxY();
  width = x2 - x1;
  height = y2 - y1;
 }

 /**
  * Update the coordinates after drawing.
  */
 protected void updateCoordinates(int x1, int y1, int x2, int y2) {
  this.x1 = x1;
  this.x2 = x2;
  this.y1 = y1;
  this.y2 = y2;
  width = x2 - x1;
  height = y2 - y1;
 }

 /**
  * Gets the shape.
  *
  * @return the shape
  */
 public Shape getShape() {
  return shape;
 }

 /**
  * Sets the shape.
  *
  * @param shape the new shape
  */
 public void setShape(Shape shape) {
  this.shape = shape;
 }

 /**
  * Gets the color.
  *
  * @return the color
  */
 public Color getColor() {
  return color;
 }

 /**
  * Sets the color.
  *
  * @param color the new color
  */
 public void setColor(Color color) {
  this.color = color;
 }

 /**
  * Checks if is fill.
  *
  * @return true, if is fill
  */
 public boolean isFill() {
  return fill;
 }

 /**
  * Sets the fill.
  *
  * @param fill the new fill
  */
 public void setFill(boolean fill) {
  this.fill = fill;
 }
}
