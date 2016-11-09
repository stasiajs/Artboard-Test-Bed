package shapes;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;

import gui.Config;

public class XRect extends XShape implements Serializable {

 public XRect() {
  shape = new Path2D.Double();
 }

 @Override
 public void dragTo(int x, int y) {
  draw(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
 }

 @Override
 public void resize(int x, int y, int corner) {

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   draw(x1, y1, x, y);
  }
  else if (corner == Config.HIT_TOP_LEFT) {
   draw(x, y, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   draw(x1, y, x, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
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

 }

 @Override
 public void changeOrientation() {
  // TODO Auto-generated method stub

 }

}
