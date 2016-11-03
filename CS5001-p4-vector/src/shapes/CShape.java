package shapes;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;


public class CShape implements Serializable {
 private Shape shape;
 private Color color;
 private boolean visible;
 
 public CShape(Shape shape, Color color) {
  this.shape = shape;
  this.color = color;
  
  visible = true;
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

 public boolean isVisible() {
  return visible;
 }

 public void setVisible(boolean visible) {
  this.visible = visible;
 }
 
// public String toString() {
//  
// }
 
 
}
