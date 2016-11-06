package shapes;

import java.awt.geom.Rectangle2D;

public class XSquare extends XRect {

 public XSquare() {
  super();
 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {

  int w = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));

  int x = x1 > x2 ? x1 - w : x1;
  int y = y1 > y2 ? y1 - w : y1;

  shape = new Rectangle2D.Double(x, y, w, w);

 }
 
 @Override
 public void dragTo(int x, int y) {
  
 }

 @Override
 public void resize(int x, int y, int corner) {

 }

}
