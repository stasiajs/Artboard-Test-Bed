package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract class XShape {

 protected Shape shape;
 private Color color;
 private boolean fill;
 private int line = 1;

 public Rectangle2D.Double[] getSelections() {
  Rectangle2D.Double[] selections = new Rectangle2D.Double[4];
  if (shape != null && !shape.equals(null)) {
   selections[0] = new Rectangle2D.Double(shape.getBounds().getX() - 3, shape.getBounds().getY() - 3, 6, 6);
   selections[1] = new Rectangle2D.Double(shape.getBounds().getMaxX() - 3, shape.getBounds().getY() - 3, 6, 6);
   selections[2] = new Rectangle2D.Double(shape.getBounds().getX() - 3, shape.getBounds().getMaxY() - 3, 6, 6);
   selections[3] = new Rectangle2D.Double(shape.getBounds().getMaxX() - 3, shape.getBounds().getMaxY() - 3, 6, 6);

   return selections;
  }

  else {
   return null;
  }

 }

 public Rectangle2D.Double[] deselect() {
  return new Rectangle2D.Double[4];
 }

 public boolean isClicked(int x, int y) {
  if (shape.contains(x, y)) {
   return true;
  }
  else {
   return false;
  }
 }

 public abstract void draw(int x1, int y1, int x2, int y2);

 public abstract void dragTo(int x, int y);

 public abstract void resize(int x, int y, int corner);

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

 public int getLine() {
  return line;
 }

 public void setLine(int line) {
  this.line = line;
 }

}
