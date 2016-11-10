package shapes;

import java.awt.geom.Path2D;

/**
 * The Class XHexagon defines a hexagon. It inherits from XShape and overrides its draw() method.
 */
public class XHexagon extends XShape {

 private final double half = 0.5;
 private final double quarter = 0.25;
 private final double threequarter = 0.75;

 /**
  * Instantiates a new XHexagon.
  */
 public XHexagon() {
  shape = new Path2D.Double();
 }

 /**
  * Draws the Hexagon according to its definition.
  *
  * @see shapes.XShape#construct(int, int, int, int)
  */
 @Override
 public void construct(int x1, int y1, int x2, int y2) {
  double width = x2 - x1;
  double height = y2 - y1;
  Path2D.Double path = new Path2D.Double();

  path.moveTo(x1 + (quarter * width), y1);
  path.lineTo(x1 + (threequarter * width), y1);
  path.lineTo(x2, y1 + (half * height));
  path.lineTo(x1 + (threequarter * width), y2);
  path.lineTo(x1 + (quarter * width), y2);
  path.lineTo(x1, y1 + (half * height));
  path.lineTo(x1 + (quarter * width), y1);
  path.closePath();

  updateCoordinates(x1, y1, x2, y2);
  shape = path;

 }
}
