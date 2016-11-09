package shapes;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;

import gui.Config;

public class XRect extends XShape implements Serializable {

// int x1 = 0;
// int x2 = 0;
// int y1 = 0;
// int y2 = 0;
// int w = 0;
// int h = 0;

 public XRect() {
  //  shape = new Rectangle2D.Double();
  shape = new Path2D.Double();
 }

 @Override
 public void dragTo(int x, int y) {
  //  Rectangle2D.Double rect = ((Rectangle2D.Double) shape);
  //  rect.setRect(x - rect.getWidth() / 2, y - rect.getHeight() / 2, rect.getWidth(), rect.getHeight());
  draw(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
 }

 @Override
 public void resize(int x, int y, int corner) {

  //  Rectangle2D.Double rect = ((Rectangle2D.Double) shape);

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   //   rect.setRect(rect.getX(), rect.getY(), x - rect.getX(), y - rect.getY());
   //   draw((int) rect.getX(), (int) rect.getY(), x, y);
      draw(x1, y1, x, y);
//   draw((int) shape.getBounds().getMinX(), (int) shape.getBounds().getMinY(), x, y);
  }
  else if (corner == Config.HIT_TOP_LEFT) {
   //   rect.setRect(x, y, rect.getMaxX() - x, rect.getMaxY() - y);
   draw(x, y, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   //   rect.setRect(rect.getX(), y, x - rect.getX(), rect.getMaxY() - y);
   draw(x1, y, x, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   //   rect.setRect(x, rect.getY(), rect.getMaxX() - x, y - rect.getY());
   draw(x, y1, x2, y);
  }

 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {

  Path2D.Double rect = new Path2D.Double();

  rect.moveTo(x1, y1);
  rect.lineTo(x2, y1);
  rect.lineTo(x2, y2);
  rect.lineTo(x1, y2);
  rect.lineTo(x1, y1);
  rect.closePath();

  this.x1 = x1;
  this.x2 = x2;
  this.y1 = y1;
  this.y2 = y2;
  this.width = x2 - x1;
  this.height = y2 - y1;
  shape = rect;

  //  Rectangle2D.Double rect = ((Rectangle2D.Double) shape);
  //
  //  if (x2 < x1 && y2 < y1) {
  //   rect.setRect(x2, y2, x1 - x2, y1 - y2);
  //  }
  //  else if (x2 < x1 && y2 >= y1) {
  //   rect.setRect(x2, y1, x1 - x2, y2 - y1);
  //  }
  //  else if (x2 >= x1 && y2 < y1) {
  //   rect.setRect(x1, y2, x2 - x1, y1 - y2);
  //  }
  //  else if (x2 >= x1 && y2 >= y1) {
  //   rect.setRect(x1, y1, x2 - x1, y2 - y1);
  //  }
 }

 @Override
 public void changeOrientation() {
  // TODO Auto-generated method stub

 }

}
