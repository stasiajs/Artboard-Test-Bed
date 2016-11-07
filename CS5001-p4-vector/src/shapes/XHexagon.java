package shapes;

import java.awt.Polygon;
import java.awt.geom.Path2D;

public class XHexagon extends XShape {
 
 public XHexagon() {
  
  shape = new Path2D.Double();
  
 }

 @Override
 public void draw(int x1, int y1, int x2, int y2) {
  System.out.println("drawing");
  double width = x2-x1;
  double height = y2-y1;
  Path2D.Double path = new Path2D.Double();
  
  path.moveTo(x1+0.25*width, y1);
  path.lineTo(x1+0.75*width, y1);
  path.lineTo(x2, y1+0.5*height);
  path.lineTo(x1+0.75*width, y2);
  path.lineTo(x1+0.25*width, y2);
  path.lineTo(x1,y1+0.5*height);
  path.lineTo(x1+0.25*width, y1);
  path.closePath();
  
  
  
//  path.moveTo(0.0, 8.0);
//  path.curveTo(0.0, 0.0, 8.0, 0.0, 8.0, 0.0);
//  path.lineTo(width - 8.0, 0.0);
//  path.curveTo(width, 0.0, width, 8.0, width, 8.0);
//  path.lineTo(width, height - 8.0);
//  path.curveTo(width, height, width - 8.0, height, width - 8.0, height);
//  path.lineTo(8.0, height);
//  path.curveTo(0.0, height, 0.0, height - 8.0, 0, height - 8.0);
//  path.closePath();
  
  shape = path;
  
//  Polygon p = new Polygon();
//  
//  p.addPoint(x1, y1);
//  p.addPoint(x2, y2);
//  p.ad
  
  
  
//  .addAll(new Double[]{
//    -SHIP_WIDTH / 2, 0.0,
//    +SHIP_WIDTH / 2, 0.0,
//    0.0, SHIP_LENGTH;
  
 }

 @Override
 public void dragTo(int x, int y) {
  // TODO Auto-generated method stub
  
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
