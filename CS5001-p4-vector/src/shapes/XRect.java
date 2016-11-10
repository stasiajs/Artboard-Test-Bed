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

  shape = rect;

 }
}
