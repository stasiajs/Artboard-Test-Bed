package shapes;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import gui.Config;

public class XRect extends XShape {

 public XRect() {
  shape = new Rectangle2D.Double();
 }

 @Override
 public void dragTo(int x, int y) {
  Rectangle2D.Double rect = ((Rectangle2D.Double) shape);
  rect.setRect(x - rect.getWidth() / 2, y - rect.getHeight() / 2, rect.getWidth(), rect.getHeight());
 }

 @Override
 public void resize(int x, int y, int corner) {

  Rectangle2D.Double rect = ((Rectangle2D.Double) shape);

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   rect.setRect(rect.getX(), rect.getY(), x - rect.getX(), y - rect.getY());
   //   draw((int) rect.getX(), (int) rect.getY(), x, y);
  }
  else if (corner == Config.HIT_TOP_LEFT) {
   rect.setRect(x, y, rect.getMaxX() - x, rect.getMaxY() - y);
  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   rect.setRect(rect.getX(), y, x - rect.getX(), rect.getMaxY() - y);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   rect.setRect(x, rect.getY(), rect.getMaxX() - x, y - rect.getY());
  }

 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {

  Rectangle2D.Double rect = ((Rectangle2D.Double) shape);

  if (x2 < x1 && y2 < y1) {
   rect.setRect(x2, y2, x1 - x2, y1 - y2);
  }
  else if (x2 < x1 && y2 >= y1) {
   rect.setRect(x2, y1, x1 - x2, y2 - y1);
  }
  else if (x2 >= x1 && y2 < y1) {
   rect.setRect(x1, y2, x2 - x1, y1 - y2);
  }
  else if (x2 >= x1 && y2 >= y1) {
   rect.setRect(x1, y1, x2 - x1, y2 - y1);
  }
 }

}
