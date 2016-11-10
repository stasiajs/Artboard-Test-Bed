package shapes;

import gui.Config;

/**
 * The Class XSquare defines a square with an identical side length. It inherits from
 */
public class XSquare extends XRect {

 /**
  * Draw a square.
  *
  * @see shapes.XRect#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {

  // find the short side
  int shortSide = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
  int newX = x1 > x2 ? x1 - shortSide : x1;
  int newY = y1 > y2 ? y1 - shortSide : y1;

  // use the super method with the side limit
  super.draw(newX, newY, newX + shortSide, newY + shortSide);
 }

 /**
  * Resize a square.
  *
  * @see shapes.XRect#resize(int, int, int)
  */
 @Override
 public void resize(int x, int y, int corner) {

  int shortSide = 0;

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   shortSide = Math.min(Math.abs(x - x1), Math.abs(y - y1));
   super.draw(x1, y1, x1 + shortSide, y1 + shortSide);

  }
  else if (corner == Config.HIT_TOP_LEFT) {
   shortSide = Math.min(Math.abs(x - x2), Math.abs(y - y2));
   super.draw(x2 - shortSide, y2 - shortSide, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   shortSide = Math.min(Math.abs(x - x1), Math.abs(y - y2));
   super.draw(x1, y2 - shortSide, x1 + shortSide, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   shortSide = Math.min(Math.abs(x - x2), Math.abs(y - y1));
   super.draw(x2 - shortSide, y1, x2, y1 + shortSide);
  }
 }

}
