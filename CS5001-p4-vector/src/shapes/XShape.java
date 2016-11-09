package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class XShape.
 */
public abstract class XShape implements Serializable {

 /** The shape. */
 protected Shape shape;

 /** The color. */
 private Color color;

 /** The fill. */
 private boolean fill = false;

 /** The x 1. */
 protected int x1 = 0;

 /** The y 1. */
 protected int y1 = 0;

 /** The x 2. */
 protected int x2 = 0;

 /** The y 2. */
 protected int y2 = 0;

 /** The width. */
 protected int width = 0;

 /** The height. */
 protected int height = 0;

 // public Rectangle2D.Double[] getSelections() {
 //  Rectangle2D.Double[] selections = new Rectangle2D.Double[4];
 //  if (shape != null && !shape.equals(null)) {
 //   selections[0] = new Rectangle2D.Double(shape.getBounds().getMinX() - 3, shape.getBounds().getMinY() - 3, 6, 6);
 //   selections[1] = new Rectangle2D.Double(shape.getBounds().getMaxX() - 3, shape.getBounds().getMinY() - 3, 6, 6);
 //   selections[2] = new Rectangle2D.Double(shape.getBounds().getMinX() - 3, shape.getBounds().getMaxY() - 3, 6, 6);
 //   selections[3] = new Rectangle2D.Double(shape.getBounds().getMaxX() - 3, shape.getBounds().getMaxY() - 3, 6, 6);
 //
 //   return selections;
 //  }
 //
 //  else {
 //   return null;
 //  }
 //
 // }

 // public Rectangle2D.Double[] deselect() {
 //  return new Rectangle2D.Double[4];
 // }

 /**
  * Checks if is clicked.
  *
  * @param x the x
  * @param y the y
  * @return true, if is clicked
  */
 public boolean isClicked(int x, int y) {
  if (shape.contains(x, y)) {
   return true;
  }
  else {
   return false;
  }
 }

 /**
  * Draw.
  *
  * @param x1 the x 1
  * @param y1 the y 1
  * @param x2 the x 2
  * @param y2 the y 2
  */
 public abstract void draw(int x1, int y1, int x2, int y2);

 /**
  * Drag to.
  *
  * @param x the x
  * @param y the y
  */
 public void dragTo(int x, int y) {
  draw(x - (width / 2), y - (height / 2), x + (width / 2), y + (height / 2));
 }

 /**
  * Resize.
  *
  * @param x the x
  * @param y the y
  * @param corner the corner
  */
 public abstract void resize(int x, int y, int corner);

 /**
  * Update bounds.
  */
 public void updateBounds() {
  x1 = (int) shape.getBounds().getMinX();
  y1 = (int) shape.getBounds().getMinY();
  x2 = (int) shape.getBounds().getMaxX();
  y2 = (int) shape.getBounds().getMaxY();
//  width=x2-x1;
//  height=y2-y1;
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

 /**
  * Gets the x1.
  *
  * @return the x1
  */
 public int getX1() {
  return x1;
 }

 /**
  * Sets the x1.
  *
  * @param x1 the new x1
  */
 public void setX1(int x1) {
  this.x1 = x1;
 }

 /**
  * Gets the y1.
  *
  * @return the y1
  */
 public int getY1() {
  return y1;
 }

 /**
  * Sets the y1.
  *
  * @param y1 the new y1
  */
 public void setY1(int y1) {
  this.y1 = y1;
 }

 /**
  * Gets the x2.
  *
  * @return the x2
  */
 public int getX2() {
  return x2;
 }

 /**
  * Sets the x2.
  *
  * @param x2 the new x2
  */
 public void setX2(int x2) {
  this.x2 = x2;
 }

 /**
  * Gets the y2.
  *
  * @return the y2
  */
 public int getY2() {
  return y2;
 }

 /**
  * Sets the y2.
  *
  * @param y2 the new y2
  */
 public void setY2(int y2) {
  this.y2 = y2;
 }

 /**
  * Gets the width.
  *
  * @return the width
  */
 public int getWidth() {
  return width;
 }

 /**
  * Sets the width.
  *
  * @param width the new width
  */
 public void setWidth(int width) {
  this.width = width;
 }

 /**
  * Gets the height.
  *
  * @return the height
  */
 public int getHeight() {
  return height;
 }

 /**
  * Sets the height.
  *
  * @param height the new height
  */
 public void setHeight(int height) {
  this.height = height;
 }

}
