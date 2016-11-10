package shapes;

import java.awt.geom.Path2D;

/**
 * The Class XHexagon defines a hexagon. It inherits from XShape and overrides its draw() method.
 */
public class XHexagon extends XShape {

 /**
  * Instantiates a new XHexagon.
  */
 public XHexagon() {
  shape = new Path2D.Double();
 }

 /**
  * @see shapes.XShape#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  double width = x2 - x1;
  double height = y2 - y1;
  Path2D.Double path = new Path2D.Double();

  path.moveTo(x1 + (0.25 * width), y1);
  path.lineTo(x1 + (0.75 * width), y1);
  path.lineTo(x2, y1 + (0.5 * height));
  path.lineTo(x1 + (0.75 * width), y2);
  path.lineTo(x1 + (0.25 * width), y2);
  path.lineTo(x1, y1 + (0.5 * height));
  path.lineTo(x1 + (0.25 * width), y1);
  path.closePath();

  updateCoordinates(x1, y1, x2, y2);
  shape = path;

 }
}
