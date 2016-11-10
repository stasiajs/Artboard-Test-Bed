package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;

import gui.Config;

/**
 * XShape describes an abstract eXtendedShape that holds an underlying shape, a color, the information if the shape shall be filled with color
 * and basic coordinates as well as height and width. Also the abstract XShape provide methods for drawing, resizing, dragging and click 
 * recognition. 
 */
public abstract class XShape implements Serializable {

 /** The underlying shape. */
 protected Shape shape;

 /** The color of the shape. */
 private Color color;

 /** Determines whether the shape is filled or not. Default is false. */
 private boolean fill = false;

 // /** The x1 coordinate of the shape's frame. */
 // protected int x1 = 0;
 //
 // /** The y1 coordinate of the shape's frame. */
 // protected int y1 = 0;
 //
 // /** The x2 coordinate of the shape's frame. */
 // protected int x2 = 0;
 //
 // /** The y2 coordinate of the shape's frame. */
 // protected int y2 = 0;
 //
 // /** The width of the shape's frame.. */
 // protected int width = 0;
 //
 // /** The height of the shape's frame. */
 // protected int height = 0;

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
  * Draw the shape.
  *
  * @param x1 the x1 coordinate of the shape's frame
  * @param y1 the y1 coordinate of the shape's frame
  * @param x2 the x2 coordinate of the shape's frame
  * @param y2 the y2 coordinate of the shape's frame
  */
 public abstract void draw(int x1, int y1, int x2, int y2);

 /**
  * Drag the shape to a specific position.
  *
  * @param x the x
  * @param y the y
  */
 public void dragTo(int x, int y) {
  draw(x - (int) (shape.getBounds2D().getWidth() / 2), y - (int) (shape.getBounds2D().getHeight() / 2),
    x + (int) (shape.getBounds2D().getWidth() / 2), y + (int) (shape.getBounds2D().getHeight() / 2));
  //  updateBounds();
 }

 /**
  * Resize.
  *
  * @param x the x
  * @param y the y
  * @param corner the corner
  */
 public void resize(int x, int y, int corner) {
  if (corner == Config.HIT_BOTTOM_RIGHT) {
   draw((int)shape.getBounds().getMinX(), (int)shape.getBounds().getMinY(), x, y);
  }
  else if (corner == Config.HIT_TOP_LEFT) {
   draw(x, y, (int)shape.getBounds().getMaxX(), (int)shape.getBounds().getMaxY());

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   draw((int)shape.getBounds().getMinX(), y, x, (int)shape.getBounds().getMaxY());
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   draw(x, (int)shape.getBounds().getMinY(), (int)shape.getBounds().getMaxX(), y);
  }

 }

 /**
  * Update the bounds.
  */
 // public void updateBounds() {
 //  x1 = (int) shape.getBounds().getMinX();
 //  y1 = (int) shape.getBounds().getMinY();
 //  x2 = (int) shape.getBounds().getMaxX();
 //  y2 = (int) shape.getBounds().getMaxY();
 //    width=x2-x1;
 //    height=y2-y1;
 // }

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

 // /**
 //  * Gets the x1 coordinate.
 //  *
 //  * @return the x1
 //  */
 // public int getX1() {
 //  return x1;
 // }
 //
 // /**
 //  * Sets the x1 coordinate.
 //  *
 //  * @param x1 the new x1
 //  */
 // public void setX1(int x1) {
 //  this.x1 = x1;
 // }
 //
 // /**
 //  * Gets the y1 coordinate.
 //  *
 //  * @return the y1
 //  */
 // public int getY1() {
 //  return y1;
 // }
 //
 // /**
 //  * Sets the y1 coordinate.
 //  *
 //  * @param y1 the new y1
 //  */
 // public void setY1(int y1) {
 //  this.y1 = y1;
 // }
 //
 // /**
 //  * Gets the x2 coordinate.
 //  *
 //  * @return the x2
 //  */
 // public int getX2() {
 //  return x2;
 // }
 //
 // /**
 //  * Sets the x2 coordinate.
 //  *
 //  * @param x2 the new x2
 //  */
 // public void setX2(int x2) {
 //  this.x2 = x2;
 // }
 //
 // /**
 //  * Gets the y2 coordinate.
 //  *
 //  * @return the y2
 //  */
 // public int getY2() {
 //  return y2;
 // }
 //
 // /**
 //  * Sets the y2 coordinate.
 //  *
 //  * @param y2 the new y2
 //  */
 // public void setY2(int y2) {
 //  this.y2 = y2;
 // }
 //
 // /**
 //  * Gets the width.
 //  *
 //  * @return the width
 //  */
 // public int getWidth() {
 //  return width;
 // }
 //
 // /**
 //  * Sets the width.
 //  *
 //  * @param width the new width
 //  */
 // public void setWidth(int width) {
 //  this.width = width;
 // }
 //
 // /**
 //  * Gets the height.
 //  *
 //  * @return the height
 //  */
 // public int getHeight() {
 //  return height;
 // }
 //
 // /**
 //  * Sets the height.
 //  *
 //  * @param height the new height
 //  */
 // public void setHeight(int height) {
 //  this.height = height;
 // }

}
