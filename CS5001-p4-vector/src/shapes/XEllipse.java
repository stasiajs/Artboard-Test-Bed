package shapes;

import java.awt.geom.Ellipse2D;

import gui.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class XEllipse.
 */
public class XEllipse extends XShape {

 /**
  * Instantiates a new x ellipse.
  */
 public XEllipse() {
  shape = new Ellipse2D.Double();
 }

 /* (non-Javadoc)
  * @see shapes.XShape#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Ellipse2D.Double) shape).setFrameFromDiagonal(x1, y1, x2, y2);

  this.x1 = x1;
  this.x2 = x2;
  this.y1 = y1;
  this.y2 = y2;
  width = x2 - x1;
  height = y2 - y1;

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
