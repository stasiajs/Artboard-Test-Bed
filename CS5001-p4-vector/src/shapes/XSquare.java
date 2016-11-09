package shapes;

import java.awt.geom.Rectangle2D;

import gui.Config;

public class XSquare extends XRect {

 public XSquare() {
  super();
 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {

  int s = Math.min(Math.abs(x2-x1), Math.abs(y2-y1));
//  
//  if (Math.abs(x2-x1) >= Math.abs(y2-y1)) {
//   s = y2-y1;
//  }
//  else {
//   s = x2-x1;
//  }
  
//  if ()
  
  
  int x = x1 > x2 ? x1 - s : x1;
  int y = y1 > y2 ? y1 - s : y1;

//  shape = new Rectangle2D.Double(x, y, s, s);
  super.draw(x,y, x+s, y+s);

 }
 
 @Override
 public void dragTo(int x, int y) {
  super.dragTo(x, y);
 }

 @Override
 public void resize(int x, int y, int corner) {
  if (corner == Config.HIT_BOTTOM_RIGHT) {
   draw(x1, y1, x, y);
  }
  else if (corner == Config.HIT_TOP_LEFT) {
   draw(x, y, x2, y2);

  }
  else if (corner == Config.HIT_TOP_RIGHT) {
   draw(x1, y, x, y2);
  }
  else if (corner == Config.HIT_BOTTOM_LEFT) {
   draw(x, y1, x2, y);
  }
 }

}
