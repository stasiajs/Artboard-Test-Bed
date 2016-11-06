package shapes;

import java.awt.geom.Line2D;

import gui.Config;

public class XLine extends XShape {

 int xComp = 0;
 int yComp = 0;

 public XLine() {
  shape = new Line2D.Double();
 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Line2D.Double) shape).setLine(x1, y1, x2, y2);

  xComp = x2 - x1;
  yComp = y2 - y1;
 }

 @Override
 public void dragTo(int x, int y) {
  Line2D.Double line = ((Line2D.Double) shape);
  //  ((Line2D.Double) shape).get

  line.setLine(x - (xComp + 1) / 2, y - (yComp + 1) / 2, x + xComp / 2, y + yComp / 2);

 }

 @Override
 public void resize(int x, int y, int corner) {
  Line2D.Double line = ((Line2D.Double) shape);

  if (corner == Config.HIT_BOTTOM_RIGHT) {
   line.setLine(line.getX1(), line.getY1(), x, y);
  }

  else if (corner == Config.HIT_TOP_RIGHT) {
   line.setLine(line.getX1(), y, x, line.getY2());
  }

  else if (corner == Config.HIT_BOTTOM_LEFT) {
   line.setLine(x, line.getY1(), line.getX2(), y);
  }

  else if (corner == Config.HIT_TOP_LEFT) {
   line.setLine(x, y, line.getX2(), line.getY2());
  }

 }

 @Override
 public boolean isClicked(int x, int y) {
  if (shape.intersects(x - 1, y - 1, 3, 3)) {
   return true;
  }
  else {
   return false;
  }
 }

 @Override
 public void changeOrientation() {
  // TODO Auto-generated method stub
  
 }

}
