package shapes;

import java.awt.geom.Line2D;

import gui.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class XLine.
 */
public class XLine extends XShape {

 /**
  * Instantiates a new x line.
  */
 public XLine() {
  shape = new Line2D.Double();
 }

 /* (non-Javadoc)
  * @see shapes.XShape#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Line2D.Double) shape).setLine(x1, y1, x2, y2);
 }

 /* (non-Javadoc)
  * @see shapes.XShape#resize(int, int, int)
  */
// @Override
// public void resize(int x, int y, int corner) {
//  if (corner == Config.HIT_BOTTOM_RIGHT) {
//   draw((int)shape.getBounds().getMinX(), (int)shape.getBounds().getMinY(), x, y);
//  }
//  else if (corner == Config.HIT_TOP_LEFT) {
//   draw(x, y, (int)shape.getBounds().getMaxX(), (int)shape.getBounds().getMaxY());
//
//  }
//  else if (corner == Config.HIT_TOP_RIGHT) {
//   draw((int)shape.getBounds().getMinX(), y, x, (int)shape.getBounds().getMaxY());
//  }
//  else if (corner == Config.HIT_BOTTOM_LEFT) {
//   draw(x, (int)shape.getBounds().getMinY(), (int)shape.getBounds().getMaxX(), y);
//  }
//
// }

 /* (non-Javadoc)
  * @see shapes.XShape#isClicked(int, int)
  */
 @Override
 public boolean isClicked(int x, int y) {
  if (shape.intersects(x - 1, y - 1, 3, 3)) {
   return true;
  }
  else {
   return false;
  }
 }
}
