package shapes;

import java.awt.geom.Path2D;

/**
 * The Class XRect defines a rectangular shape using a Path2D.Double as an the underlying Shape. It inherits from XShape and overrides its draw method.
 */
public class XRect extends XShape {

 /**
  * Instantiates a new XRect.
  */
 public XRect() {
  shape = new Path2D.Double();
 }

 /**
  * Construct a rectangular shape using a Path2D.Double.
  *
  * @see shapes.XShape#construct(int, int, int, int)
  */
 @Override
 public void construct(int x1, int y1, int x2, int y2) {

  Path2D.Double rect = new Path2D.Double();

  rect.moveTo(x1, y1);
  rect.lineTo(x2, y1);
  rect.lineTo(x2, y2);
  rect.lineTo(x1, y2);
  rect.lineTo(x1, y1);
  rect.closePath();

  updateCoordinates(x1, y1, x2, y2);
  shape = rect;

 }
}
