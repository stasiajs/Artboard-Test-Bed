package shapes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Path2D;

import javax.swing.JTextPane;

public class XText extends XShape {
	
	private Font font;
	private String text;
	private JTextPane textPane;

	@Override
	public void construct(int x1, int y1, int x2, int y2) {
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		
		// make a rectangle so that the whole thing doesn't break
		/*
		Path2D.Double rect = new Path2D.Double();
		rect.moveTo(x1, y1);
		rect.lineTo(x2, y1);
		rect.lineTo(x2, y2);
		rect.lineTo(x1, y2);
		rect.lineTo(x1, y1);
		rect.closePath();
		*/
		
		Rectangle rect = new Rectangle(x1, y1, x2-x1, y2-y1);
		
		updateCoordinates(x1, y1, x2, y2);
		shape = rect;
	}

	@Override
	public void paint(Graphics2D g) {
		setText(textPane.getText());
		
		if (text.length() > 0) {
			FontRenderContext frc = g.getFontRenderContext();
			TextLayout layout = new TextLayout(text, font, frc);
			
			textPane.setVisible(false);
			// draw text in the middle of the rectangle:
			//float x = x1 + width/2;
			//float y = y1 + height/2;
			layout.draw(g, (float)x1, (float)y1 + 15);
		} 
		//g.draw(shape);
	}
	
	public void setText(String textEntry) {
		text = textEntry;
	}
	
	public void setTextPane(JTextPane tp) {
		textPane = tp;
		textPane.setBounds(x1, y1, width, height);
	}
	
	public void setEditable(boolean isEditable) {
		textPane.setEditable(isEditable);
	}
	
	public void setFontSize(int newSize) {
		font = new Font(Font.SANS_SERIF, Font.PLAIN, newSize);
	}
}
