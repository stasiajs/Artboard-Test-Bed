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

 }


}
