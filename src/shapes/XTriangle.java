package shapes;

import java.awt.geom.Path2D;

public class XTriangle extends XShape {

	public XTriangle () {
		shape = new Path2D.Double();
	}
	
	@Override
	public void construct(int x1, int y1, int x2, int y2) {
		double tWidth = x2 - x1;
		double tHeight = y2 - y1;
		
		double firstX = (tWidth / 2.0) * (1 - 1 / Math.sqrt(3));
	    double firstY = 3.0 * tHeight / 4.0;
		
		Path2D.Double path = new Path2D.Double();

		path.moveTo(firstX, firstY);
		path.lineTo(tWidth - firstX, firstY);
		path.lineTo(tWidth / 2.0, tHeight / 4.0);
		//path.moveTo(x1 + (tWidth/2), tHeight/2);
		//path.lineTo((tWidth/2),(tHeight/2)-50);
		//path.lineTo((tWidth/2)-50, tHeight/2);
		path.closePath();

		 updateCoordinates(x1, y1, x2, y2);
		 shape = path;
	}

}
