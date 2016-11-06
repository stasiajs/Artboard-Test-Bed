package shapes;

import java.awt.geom.Ellipse2D;

public class XCircle extends XEllipse{
 
 public XCircle() {
  super();
 }
 
 @Override
 public void draw(int x1, int y1, int x2, int y2){
  
  int r = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
  
  int x = x1 > x2 ? x1 - r : x1;
  int y = y1 > y2 ? y1 - r : y1;
  
  
  shape = new Ellipse2D.Double(x, y, r, r);
  
 }

}
