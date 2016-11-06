package shapes;

import java.awt.geom.Ellipse2D;

public class XEllipse extends XShape {

 public XEllipse() {
  shape = new Ellipse2D.Double();
 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Ellipse2D.Double) shape).setFrameFromDiagonal(x1, y1, x2, y2);

 }

 @Override
 public void dragTo(int x, int y) {
  Ellipse2D.Double ellipse = (Ellipse2D.Double) shape;
  ellipse.setFrame(x - ellipse.getWidth() / 2, y - ellipse.getHeight() / 2, ellipse.getWidth(), ellipse.getHeight());

 }

 @Override
 public void resize(int x, int y, int corner) {
  // TODO Auto-generated method stub

 }

 @Override
 public void changeOrientation() {
  // TODO Auto-generated method stub

 }

}
