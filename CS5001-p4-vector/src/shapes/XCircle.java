package shapes;

import gui.Config;

/**
 * The Class XCircle defines a Circle. It inherits from ellipse and overrides its draw() and resize() method, as a circle needs a fixed radius.
 */
public class XCircle extends XEllipse {

 /**
  * Construct the circle. The x and y components of the radius are the shorter side of the drawn rectangle.
  *
  * @see shapes.XEllipse#construct(int, int, int, int)
  */
 @Override
 public void construct(int x1, int y1, int x2, int y2) {

  // find out the x and y components of the radius as the shortest side
  int radius = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));

  // use the ellipse method to draw the circle using the x and y component of the radius
  int newX = x1 > x2 ? x1 - radius : x1;
  int newY = y1 > y2 ? y1 - radius : y1;

  super.construct(newX, newY, newX + radius, newY + radius);

 }

 /**
  * Resize the circle. The shortest side of the rectangle is taken as for the x and y components of the radius.
  *
  * @see shapes.XEllipse#resize(int, int, int)
  */
 @Override
 public void resize(int x, int y, int corner) {
  int s = 0;
  if (corner == Config.HIT_BOTTOM_RIGHT) {
   s = Math.min(Math.abs(x - x1), Math.abs(y - y1));
   super.construct(x1, y1, x1 + s, y1 + s);

  }
  else if (corner == Config.HIT_TOP_LEFT) {
   s = Math.min(Math.abs(x - x2), Math.abs(y - y2));
   super.construct(x2 - s, y2 - s, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   s = Math.min(Math.abs(x - x1), Math.abs(y - y2));
   super.construct(x1, y2 - s, x1 + s, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   s = Math.min(Math.abs(x - x2), Math.abs(y - y1));
   super.construct(x2 - s, y1, x2, y1 + s);
  }
 }

}
