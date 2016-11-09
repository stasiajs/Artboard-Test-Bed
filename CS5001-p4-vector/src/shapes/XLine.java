package shapes;

import java.awt.geom.Line2D;

import gui.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class XLine.
 */
public class XLine extends XShape {

 /**
  * Instantiates a new x line.
  */
 public XLine() {
  shape = new Line2D.Double();
 }

 /* (non-Javadoc)
  * @see shapes.XShape#draw(int, int, int, int)
  */
 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  ((Line2D.Double) shape).setLine(x1, y1, x2, y2);

  this.x1 = x1;
  this.x2 = x2;
  this.y1 = y1;
  this.y2 = y2;
  width = x2 - x1;
  height = y2 - y1;

 }

 /* (non-Javadoc)
  * @see shapes.XShape#resize(int, int, int)
  */
 @Override
 public void resize(int x, int y, int corner) {
  //  if (x2 == getShape().getBounds().getMaxX() && y2 == getShape().getBounds().getMaxY()) {
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
  //  }
  //  else {
  //   if (corner == Config.HIT_BOTTOM_RIGHT) {
  //    draw(x, y1, x2, y);
  //   }
  //   else if (corner == Config.HIT_TOP_LEFT) {
  //    draw(x1, y, x, y2);
  //
  //   }
  //   else if (corner == Config.HIT_TOP_RIGHT) {
  //    draw(x, y, x2, y2);
  //   }
  //   else if (corner == Config.HIT_BOTTOM_LEFT) {
  //    draw(x1, y1, x, y);
  //   }
  //  }
 }

 /* (non-Javadoc)
  * @see shapes.XShape#isClicked(int, int)
  */
 @Override
 public boolean isClicked(int x, int y) {
  if (shape.intersects(x - 1, y - 1, 3, 3)) {
   return true;
  }
  else {
   return false;
  }
 }

 // @Override
 // public void updateBounds(){
 //
 //  if (x1<=x2){
 //   this.x1=(int) shape.getBounds().getMinX();
 //   System.out.println("minX: "+x1);
 //   this.x2=(int) shape.getBounds().getMaxX();
 //   System.out.println("maxX: "+x2);
 //
 //  }
 //
 //  else {
 //   this.x2=(int) shape.getBounds().getMinX();
 //   System.out.println("minX: "+x1);
 //   this.x1=(int) shape.getBounds().getMaxX();
 //   System.out.println("maxX: "+x2);
 //  }
 //
 //  if (y1<=y2){
 //   this.y1=(int) shape.getBounds().getMinY();
 //   System.out.println("minY: "+y1);
 //
 //   this.y2=(int) shape.getBounds().getMaxY();
 //   System.out.println("maxY: "+y2);
 //  }
 //
 //  else{
 //   this.y2=(int) shape.getBounds().getMinY();
 //   System.out.println("minY: "+y1);
 //
 //   this.y1=(int) shape.getBounds().getMaxY();
 //   System.out.println("maxY: "+y2);
 //  }
 //
 //
 // }

}
