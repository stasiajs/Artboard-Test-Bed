package shapes;

import java.awt.geom.Line2D;

import gui.Config;

/**
 * The Class XLine defines the shape of a line. It has an underlying Line2D.Double as a shape. XLine inherits from XShape and overrides its methods draw() and isClicked().
 */
public class XLine extends XShape {

 /**
  * Instantiates a new XLine.
  */
 public XLine() {
  shape = new Line2D.Double();
 }

 /**
  * Draws a new line with the two coordinates x1/y1 and x2/y2.
  * 
  * @see shapes.XShape#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Line2D.Double) shape).setLine(x1, y1, x2, y2);
  updateCoordinates(x1, y1, x2, y2);

 }

 /**
  * Checks if a line is clicked on. Uses the intersects() method of Shape, as the contain() method does not work for lines.
  * 
  * @see shapes.XShape#isClicked(int, int)
  */
 @Override
 public boolean isClicked(int x, int y) {
  return shape.intersects(x - 1, y - 1, Config.INTERSECT_SIZE, Config.INTERSECT_SIZE);
 }

}
