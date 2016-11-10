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

  int shortSide = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
  
  int newX = x1 > x2 ? x1 - shortSide : x1;
  int newY = y1 > y2 ? y1 - shortSide : y1;

  super.draw(newX, newY, newX + shortSide, newY + shortSide);
 }


 /** 
  * @see shapes.XRect#resize(int, int, int)
  */
 @Override
 public void resize(int x, int y, int corner) {

  int s = 0;

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   s = Math.min(Math.abs(x - x1), Math.abs(y - y1));
   super.draw(x1, y1, x1 + s, y1 + s);

  }
  else if (corner == Config.HIT_TOP_LEFT) {
   s = Math.min(Math.abs(x - x2), Math.abs(y - y2));
   super.draw(x2 - s, y2 - s, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   s = Math.min(Math.abs(x - x1), Math.abs(y - y2));
   super.draw(x1, y2 - s, x1 + s, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   s = Math.min(Math.abs(x - x2), Math.abs(y - y1));
   super.draw(x2 - s, y1, x2, y1 + s);
  }
 }

}
