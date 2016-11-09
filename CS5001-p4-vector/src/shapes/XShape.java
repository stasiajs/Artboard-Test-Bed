package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class XShape implements Serializable {

 protected Shape shape;
 private Color color;
 private boolean fill;

 protected int x1 = 0;
 protected int y1 = 0;
 protected int x2 = 0;
 protected int y2 = 0;
 protected int width = 0;
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

 public boolean isClicked(int x, int y) {
  if (shape.contains(x, y)) {
   return true;
  }
  else {
   return false;
  }
 }

 public abstract void draw(int x1, int y1, int x2, int y2);

 public void dragTo(int x, int y) {
  draw(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
 }

 public abstract void resize(int x, int y, int corner);

 public void updateBounds() {
  this.x1 = (int) shape.getBounds().getMinX();
  this.y1 = (int) shape.getBounds().getMinY();
  this.x2 = (int) shape.getBounds().getMaxX();
  this.y2 = (int) shape.getBounds().getMaxY();
 }

 public Shape getShape() {
  return shape;
 }

 public void setShape(Shape shape) {
  this.shape = shape;
 }

 public Color getColor() {
  return color;
 }

 public void setColor(Color color) {
  this.color = color;
 }

 public boolean isFill() {
  return fill;
 }

 public void setFill(boolean fill) {
  this.fill = fill;
 }

 public int getX1() {
  return x1;
 }

 public void setX1(int x1) {
  this.x1 = x1;
 }

 public int getY1() {
  return y1;
 }

 public void setY1(int y1) {
  this.y1 = y1;
 }

 public int getX2() {
  return x2;
 }

 public void setX2(int x2) {
  this.x2 = x2;
 }

 public int getY2() {
  return y2;
 }

 public void setY2(int y2) {
  this.y2 = y2;
 }

 public int getWidth() {
  return width;
 }

 public void setWidth(int width) {
  this.width = width;
 }

 public int getHeight() {
  return height;
 }

 public void setHeight(int height) {
  this.height = height;
 }

}
