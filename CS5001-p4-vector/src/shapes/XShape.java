package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract class XShape {

 protected Shape shape;
 private Color color;
 private boolean fill;
 private int line = 1;

 public abstract Rectangle2D.Double[] select();

 public Rectangle2D.Double[] deselect() {
  return new Rectangle2D.Double[4];
 }
 
 public abstract boolean isClicked();
 
 public abstract void draw(int x1, int y1, int x2, int y2);

 public abstract void drag();

 public abstract void resize();

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
