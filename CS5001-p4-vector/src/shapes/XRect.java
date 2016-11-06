package shapes;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class XRect extends XShape {

 public XRect() {
  shape = new Rectangle2D.Double();
 }

 @Override
 public Double[] select() {
  return null;
 }

 @Override
 public void drag() {

 }

 @Override
 public void resize() {

 }

 @Override
 public boolean isClicked() {
  return false;
 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  if (x2 < x1 && y2 < y1) {
   ((Rectangle2D.Double) shape).setRect(x2, y2, x1 - x2, y1 - y2);
  }
  else if (x2 < x1 && y2 >= y1) {
   ((Rectangle2D.Double) shape).setRect(x2, y1, x1 - x2, y2 - y1);
  }
  else if (x2 >= x1 && y2 < y1) {
   ((Rectangle2D.Double) shape).setRect(x1, y2, x2 - x1, y1 - y2);
  }
  else {
   ((Rectangle2D.Double) shape).setRect(x1, y1, x2 - x1, y2 - y1);
  }
 }

}
