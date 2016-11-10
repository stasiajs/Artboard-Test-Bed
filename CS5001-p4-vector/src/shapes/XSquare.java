package shapes;

import gui.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class XSquare.
 */
public class XSquare extends XRect {

 /* (non-Javadoc)
  * @see shapes.XRect#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {

  int s = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
  int x = x1 > x2 ? x1 - s : x1;
  int y = y1 > y2 ? y1 - s : y1;

  super.draw(x, y, x + s, y + s);
 }


 /* (non-Javadoc)
  * @see shapes.XRect#resize(int, int, int)
  */
 @Override
 public void resize(int x, int y, int corner) {

  int s = 0;

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   s = Math.min(Math.abs(x - (int)shape.getBounds().getMinX()), Math.abs(y - (int)shape.getBounds().getMinY()));
   super.draw((int)shape.getBounds().getMinX(), (int)shape.getBounds().getMinY(), (int)shape.getBounds().getMinX() + s, (int)shape.getBounds().getMinY() + s);

  }
  else if (corner == Config.HIT_TOP_LEFT) {
   s = Math.min(Math.abs(x - (int)shape.getBounds().getMaxX()), Math.abs(y - (int)shape.getBounds().getMaxY()));
   super.draw((int)shape.getBounds().getMaxX() - s, (int)shape.getBounds().getMaxY() - s, (int)shape.getBounds().getMaxX(), (int)shape.getBounds().getMaxY());

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   s = Math.min(Math.abs(x - (int)shape.getBounds().getMinX()), Math.abs(y - (int)shape.getBounds().getMaxY()));
   super.draw((int)shape.getBounds().getMinX(), (int)shape.getBounds().getMaxY() - s, (int)shape.getBounds().getMinX() + s, (int)shape.getBounds().getMaxY());
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   s = Math.min(Math.abs(x - (int)shape.getBounds().getMaxX()), Math.abs(y - (int)shape.getBounds().getMinY()));
   super.draw((int)shape.getBounds().getMaxX() - s, (int)shape.getBounds().getMinY(), (int)shape.getBounds().getMaxX(), (int)shape.getBounds().getMinY() + s);
  }
 }

}
