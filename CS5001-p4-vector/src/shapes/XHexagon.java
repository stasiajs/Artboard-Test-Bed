package shapes;

import java.awt.geom.Path2D;

import gui.Config;

public class XHexagon extends XShape {

 public XHexagon() {

  shape = new Path2D.Double();

 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  System.out.println("drawing");
  double width = x2 - x1;
  double height = y2 - y1;
  Path2D.Double path = new Path2D.Double();

  path.moveTo(x1 + 0.25 * width, y1);
  path.lineTo(x1 + 0.75 * width, y1);
  path.lineTo(x2, y1 + 0.5 * height);
  path.lineTo(x1 + 0.75 * width, y2);
  path.lineTo(x1 + 0.25 * width, y2);
  path.lineTo(x1, y1 + 0.5 * height);
  path.lineTo(x1 + 0.25 * width, y1);
  path.closePath();

  this.x1 = x1;
  this.x2 = x2;
  this.y1 = y1;
  this.y2 = y2;
  this.width = x2 - x1;
  this.height = y2 - y1;

  shape = path;


 }

 @Override
 public void resize(int x, int y, int corner) {
  //  Path2D.Double path = ((Path2D.Double) shape);
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

}
