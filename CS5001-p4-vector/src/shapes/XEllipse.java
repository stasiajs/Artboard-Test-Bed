package shapes;

import java.awt.geom.Ellipse2D;

/**
 * The Class XEllipse defines an ellipse. It inherits from XShape and overrides its draw() method.
 */
public class XEllipse extends XShape {

 /**
  * Instantiates a new XEllipse.
  */
 public XEllipse() {
  shape = new Ellipse2D.Double();
 }

 /**
  * Draw an ellipse. The setFrameFromDiagonal() method is used as it is a sound solution when drawing by mouse.
  * 
  * @see shapes.XShape#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Ellipse2D.Double) shape).setFrameFromDiagonal(x1, y1, x2, y2);
  updateCoordinates(x1, y1, x2, y2);
 }

}
