package shapes;

import java.awt.geom.Path2D;

import gui.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class XRect.
 */
public class XRect extends XShape {

 /**
  * Instantiates a new x rect.
  */
 public XRect() {
  shape = new Path2D.Double();
 }

 /* (non-Javadoc)
  * @see shapes.XShape#draw(int, int, int, int)
  */
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
  width = x2 - x1;
  height = y2 - y1;
  shape = rect;

 }

 /* (non-Javadoc)
  * @see shapes.XShape#resize(int, int, int)
  */
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
}
