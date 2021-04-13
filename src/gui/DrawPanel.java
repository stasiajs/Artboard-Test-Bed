package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.swing.JPanel;

import javax.speech.synthesis.Voice;
//import com.sun.speech.freetts.Voice;

import model.Model;
import shapes.XCircle;
import shapes.XEllipse;
import shapes.XHexagon;
import shapes.XLine;
import shapes.XRect;
import shapes.XShape;
import shapes.XSquare;
import shapes.XTriangle;
/**
 * The Class DrawPanel draws all the shapes of the shape list on a JPanel. It is also responsible for displaying changes regarding resizing and moving.
 */
public class DrawPanel extends JPanel {

 /** The model with the underlying shapes. */
 private Model model;

 /** X position, when mouse is pressed. */
 private int pressX = 0;

 /** Y position, when mouse is pressed. */
 private int pressY = 0;

 /** Temporary reference to the XShape that is being drawn. */
 private XShape drawXShape;

 /** Temporary reference to the XShape that is selected. */
 private XShape selectedXShape;
 
 /** Temporary reference to the XShape that is being moused over */
 private XShape detectedXShape;

 /** The current drawing color. */
 private Color color;

 /** The current drawing mode of shapes. Drawing lines is default. */
 private int shapeMode = Config.DRAW_LINE;

 /** Defines the current mode. Is either DRAW_MODE or SELECT_MODE. DRAW_MODE is default. */
 private int mode = Config.DRAW_MODE;

 /** Defines if the shapes are being filled or not. False is default. */
 private boolean setFill = false;

 /** Turns true, when a shape is being resized. */
 private boolean resize = false;

 /** Turns true, when a shape is being dragged.*/
 private boolean drag = false;

 /** Turns true, when a shape is being exported and makes the resizeBoxes invisible for the exported image. */
 private boolean export = false;

 /** The number of the resize box that is hit when resizing the selected shape. NOT_HIT (4) is default. */
 private int hitResizedBox = Config.NOT_HIT;

 /** The resize boxes that appear when selecting a shape. */
 private Rectangle2D.Double[] resizeBoxes;
 
 /** TTS synthesizer */
 private Synthesizer synthesizer;

 /**
  * Instantiates a new draw panel.
  *
  * @param model the model with the list of shapes
  */
 public DrawPanel(Model model) {

  this.model = model;
  resizeBoxes = new Rectangle2D.Double[Config.BOX_INT];

  this.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
  setBackground(Color.WHITE);
  setVisible(true);
  color = Color.BLACK;
  
  // TTS stuff
	//Set property as Kevin Dictionary 
	  System.setProperty( 
	      "freetts.voices", 
	      "com.sun.speech.freetts.en.us"
	          + ".cmu_us_kal.KevinVoiceDirectory"); 
	
	  // Register Engine 
	  try {
		Central.registerEngineCentral( 
		      "com.sun.speech.freetts"
		      + ".jsapi.FreeTTSEngineCentral");
	  // Create a Synthesizer 
	  synthesizer = Central.createSynthesizer( 
	          new SynthesizerModeDesc(Locale.US)); 
	  
	  // Allocate synthesizer 
	     synthesizer.allocate(); 
	     
	     Voice kevin = new Voice("kevin16", 
			        Voice.GENDER_DONT_CARE, Voice.AGE_DONT_CARE, null);
		  
		  synthesizer.getSynthesizerProperties().setVoice(kevin);

	     // Resume Synthesizer 
	     synthesizer.resume(); 
	  
	  } catch (EngineException e1) {
			
			e1.printStackTrace();
		
	} catch (AudioException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (EngineStateError e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (PropertyVetoException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
		
  
  addMouseListener(new MouseListener() {

   @Override
   public void mouseClicked(MouseEvent e) {

    // reset selection at mouse click
    selectedXShape = null;
    // when mouse is clicked in select mode, go through the list of shapes and get a shape if one was hit
    //if (mode == Config.SELECT_MODE) {
     selectedXShape = model.getXShapeAtPos(e.getX(), e.getY());

     // show the resize boxes of the selected shape
     if ((selectedXShape != null) && !selectedXShape.equals(null)) {
      resizeBoxes = getSelections(selectedXShape);
     //}
    }
   }

   @Override
   public void mouseReleased(MouseEvent e) {

    // stop dragging when released
    drag = false;
    // stop resizing when released
    resize = false;
    hitResizedBox = Config.NOT_HIT;

    // reset selection boxes for the selected element as soon as the mouse was released
    if (mode == Config.SELECT_MODE) {
     if ((selectedXShape != null) && !selectedXShape.equals(null)) {
      selectedXShape.updateBounds();
      resizeBoxes = getSelections(selectedXShape);
     // getTTSDescription();
     }
    }

    /*
    // if in draw mode and a new shape was drawn, add it to the list
    else if (mode == Config.DRAW_MODE) {
     if ((drawXShape != null) && !drawXShape.equals(null)) {
      model.addShape(drawXShape);
     }
     drawXShape = null;
    } */

    repaint();

   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
    // save the first x and y position as soon as the mouse is pressed
    pressX = e.getX();
    pressY = e.getY();
   }
  });

  addMouseMotionListener(new MouseMotionListener() {

   @Override
   public void mouseDragged(MouseEvent e) {

    // preview of the shape that is being drawn when in draw mode
    if (mode == Config.DRAW_MODE) {
     switch (shapeMode) {
      case Config.DRAW_LINE:
       drawXShape = new XLine();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.construct(pressX, pressY, e.getX(), e.getY());
       break;
       
      case Config.DRAW_TRIANGLE:
    	drawXShape = new XTriangle();
    	drawXShape.setColor(color);
        drawXShape.setFill(setFill);
        drawXShape.construct(pressX, pressY, e.getX(), e.getY());
        break;

      case Config.DRAW_RECT:
       drawXShape = new XRect();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.construct(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_SQUARE:
       drawXShape = new XSquare();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.construct(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_CIRCLE:
       drawXShape = new XCircle();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.construct(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_ELLIPSE:
       drawXShape = new XEllipse();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.construct(pressX, pressY, e.getX(), e.getY());
       break;

      case Config.DRAW_HEX:
       drawXShape = new XHexagon();
       drawXShape.setColor(color);
       drawXShape.setFill(setFill);
       drawXShape.construct(pressX, pressY, e.getX(), e.getY());
       break;

      default:
       break;

     }
    }

    // in select mode check if one of the resize boxes was hit and resize accordingly
    // if not check if the mouse was dragged on the element and drag it accordingly
    else if ((mode == Config.SELECT_MODE) && (selectedXShape != null) && !selectedXShape.equals(null)) {

     int tempHitSelection = getResizeBoxNumber(e.getX(), e.getY());

     // if one of the boxes was dragged, start the resize mode
     if (!resize && (tempHitSelection >= 0) && (tempHitSelection < Config.NOT_HIT)) {
      model.addUndoAction();
      hitResizedBox = tempHitSelection;
      resize = true;
     }

     // execute the resize mode as long as the mouse is pressed
     else if (resize) {
      resizeBoxes = getSelections(selectedXShape);
      selectedXShape.resize(e.getX(), e.getY(), hitResizedBox);
     }

     // check if the shape was clicked and initiate the drag mode if so
     else if (selectedXShape.isClicked(e.getX(), e.getY()) && !drag) {
      model.addUndoAction();
      drag = true;
     }

     // execute the drag mode as long as the mouse is clicked
     else if (drag) {
      selectedXShape.dragTo(e.getX(), e.getY());
      resizeBoxes = getSelections(selectedXShape);
     }
    }

    repaint();
   }

   @Override
   public void mouseMoved(MouseEvent e) {
	    // go through the list of shapes and get a shape if one was hit
	    //detectedXShape = model.getXShapeAtPos(e.getX(), e.getY());

	    //System.out.println(e.getX() + " " + e.getY());
	     // determine what type of shape it is and print it out'
	   /*
	    if ((detectedXShape != null) && !detectedXShape.equals(null)) {
	    	 //System.out.println("Shape detected: " + detectedXShape.getClass().getName());
	    	 if (detectedXShape instanceof XLine) {
	    		 System.out.println("Shape detected: Line");
	    	 } else if (detectedXShape instanceof XSquare) {
	    		 System.out.println("Shape detected: Square");
	    	 } else if (detectedXShape instanceof XRect) {
	    		 System.out.println("Shape detected: Rectangle");
	    	 } else if (detectedXShape instanceof XCircle) {
	    		 System.out.println("Shape detected: Circle");
	    	 } else if (detectedXShape instanceof XEllipse) {
	    		 System.out.println("Shape detected: Ellipse");
	    	 } else if (detectedXShape instanceof XHexagon) {
	    		 System.out.println("Shape detected: Hexagon");
	    	 } else if (detectedXShape instanceof XTriangle) {
	    		 System.out.println("Shape detected: Triangle");
	    	 }
	     }
	     */
	    
   }
  });

 }

 /**
  * The paintComponent paints all the shapes on the panel. First all the shapes from the list are painted.
  * Then if a new shape is being drawn, it gets painted.
  * At last the selection boxes are added, if there is a selected element and if the image is not being exported to PNG at the moment.
  *
  * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
  */
 @Override
 public void paintComponent(Graphics graphics) {
  Graphics2D g = (Graphics2D) graphics;
  super.paintComponent(g);

  // paint the shapes from the list
  for (int i = 0; i < model.getShapeList().size(); i++) {
   XShape xshape = model.getShapeList().get(i);
   xshape.paint(g);
   
  }

  // paint the shape that is currently being drawn
  if ((drawXShape != null) && !drawXShape.equals(null)) {
	  
   g.setColor(color);
   if (drawXShape.isFill()) {
    g.fill(drawXShape.getShape());
   }
   g.draw(drawXShape.getShape());
  }
  g.drawString("Hello world", 70, 20);

  // add the selection marks if applicable
  if (!export && (resizeBoxes != null) && !resizeBoxes.equals(null) && (selectedXShape != null)
    && !selectedXShape.equals(null)) {
	  
   for (int i = 0; i < resizeBoxes.length; i++) {
    if ((resizeBoxes[i] != null) && !resizeBoxes[i].equals(null)) {
    	g.setStroke(new BasicStroke(1));
    	g.setColor(Color.BLACK);
    	g.draw(resizeBoxes[i]);

    }
   }
  }
 }

 /**
  * Gets the color.
  *
  * @return the color
  */
 Color getColor() {
  return color;
 }

 /**
  * Sets the color.
  *
  * @param color the new color
  */
 void setColor(Color color) {
  this.color = color;
 }

 /**
  * Sets the draw mode.
  *
  * @param drawMode the new draw mode
  */
 public void setDrawMode(int drawMode) {
  shapeMode = drawMode;
 }

 /**
  * Sets the mode.
  *
  * @param mode the new mode
  */
 public void setMode(int mode) {
  this.mode = mode;
 }
 

 /**
  * Gets the selected XShape.
  *
  * @return the selected XShape
  */
 public XShape getSelectedXShape() {
  return selectedXShape;
 }

 /**
  * Sets the selected XShape.
  *
  * @param selectedXShape the new selected XShape
  */
 public void setSelectedXShape(XShape selectedXShape) {
  this.selectedXShape = selectedXShape;
 }
 
 /**
  * Draws a "default" version of the selected XShape.
  * 
  * @param the selected XShape
  */
 public void drawDefaultSelectedShape() {
	 if (mode == Config.DRAW_MODE) {
	     switch (shapeMode) {
	     case Config.DRAW_LINE:
	         drawXShape = new XLine();
	         drawXShape.setColor(color);
	         drawXShape.setFill(setFill);
	         drawXShape.construct(176, 206, 368, 329);
		      model.addShape(drawXShape);
	         break;
	         
	     case Config.DRAW_TRIANGLE:
	    	 drawXShape = new XTriangle();
	    	 drawXShape.setColor(color);
	         drawXShape.setFill(setFill);
	         drawXShape.construct(176, 206, 368, 329);
		      model.addShape(drawXShape);
	         break;
	         
	       case Config.DRAW_RECT:
	         drawXShape = new XRect();
	         drawXShape.setColor(color);
	         drawXShape.setFill(setFill);
	         drawXShape.construct(176, 206, 368, 329);
		      model.addShape(drawXShape);
	         break;
	         
	      case Config.DRAW_SQUARE:
	       drawXShape = new XSquare();
	       drawXShape.setColor(color);
	       drawXShape.setFill(setFill);
	       drawXShape.construct(176, 206, 368, 329);
	       model.addShape(drawXShape);
	       break;
	       
	      case Config.DRAW_CIRCLE:
	          drawXShape = new XCircle();
	          drawXShape.setColor(color);
	          drawXShape.setFill(setFill);
	          drawXShape.construct(176, 206, 368, 329);
		      model.addShape(drawXShape);
	          break;

	      case Config.DRAW_ELLIPSE:
	          drawXShape = new XEllipse();
	          drawXShape.setColor(color);
	          drawXShape.setFill(setFill);
	          drawXShape.construct(176, 206, 368, 329);
		      model.addShape(drawXShape);
	          break;

	      case Config.DRAW_HEX:
	          drawXShape = new XHexagon();
	          drawXShape.setColor(color);
	          drawXShape.setFill(setFill);
	          drawXShape.construct(176, 206, 368, 329);
		      model.addShape(drawXShape);
	          break;

	         default:
	          break;
	     }
	 }
 }

 /**
  * Gets the number of the resize box that was clicked.
  *
  * @param x the x coordinate of the mouse click
  * @param y the y coordinate of the mouse click
  * @return the array of resized boxes
  */
 private int getResizeBoxNumber(int x, int y) {
  if ((resizeBoxes != null) && !resizeBoxes.equals(null)) {
   for (int i = 0; i < resizeBoxes.length; i++) {
    if ((resizeBoxes[i] != null) && !resizeBoxes[i].equals(null)) {
     if (resizeBoxes[i].contains(x, y)) {
      return i;
     }
    }
   }
  }
  return Config.NOT_HIT;
 }

 /**
  * Gets an array of the resize boxes.
  *
  * @param xshape the selected shape that needs resize boxes
  * @return an array of resize boxes
  */
 public Rectangle2D.Double[] getSelections(XShape xshape) {

  Rectangle2D.Double[] selections = new Rectangle2D.Double[Config.BOX_INT];
  if ((xshape != null) && !xshape.equals(null)) {

   selections[Config.HIT_TOP_LEFT] = new Rectangle2D.Double(xshape.getShape().getBounds().getMinX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMinY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);
   selections[Config.HIT_TOP_RIGHT] = new Rectangle2D.Double(xshape.getShape().getBounds().getMaxX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMinY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);
   selections[Config.HIT_BOTTOM_LEFT] = new Rectangle2D.Double(
     xshape.getShape().getBounds().getMinX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMaxY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);
   selections[Config.HIT_BOTTOM_RIGHT] = new Rectangle2D.Double(
     xshape.getShape().getBounds().getMaxX() - Config.BOX_SIZE,
     xshape.getShape().getBounds().getMaxY() - Config.BOX_SIZE, 2 * Config.BOX_SIZE, 2 * Config.BOX_SIZE);

   return selections;
  }

  else {
   return null;
  }

 }

 /**
  * Checks if shapes are being filled when drawn.
  *
  * @return true, if is sets the fill
  */
 public boolean isSetFill() {
  return setFill;
 }

 /**
  * Sets if shapes shall be filled when drawn.
  *
  * @param setFill the new sets the fill
  */
 public void setSetFill(boolean setFill) {
  this.setFill = setFill;
 }

 /**
  * Checks if the shapes are being exported to PNG.
  *
  * @return true, if exporting
  */
 public boolean isExporting() {
  return export;
 }

 /**
  * Sets the PNG export mode to false or true.
  *
  * @param export the export mode
  */
 public void setExporting(boolean export) {
  this.export = export;
 }

 /**
  * Sets the model.
  *
  * @param model the new model
  */
 public void setModel(Model model) {
  this.model = model;
 }
 
 /**
  * Gets the model.
  */
 public Model getModel() {
	 return this.model;
 }
 
 /**
  * Sets drag mode
  */
 public void setDrag(boolean isDrag) {
	 this.drag = isDrag;
 }
 
 public void moveXShape(int newX, int newY) {
	 if (drag) {
	    selectedXShape.dragTo(newX, newY);
	    resizeBoxes = getSelections(selectedXShape);
	 }
 }
 
 /**
  * TTS shit
  */
 public void getTTSDescription() {
	 try { 
	    
	     String description = "";
	     
	     if (selectedXShape instanceof XLine) {
    		 description = "Line";
    	 } else if (selectedXShape instanceof XSquare) {
    		 description = "Square";
    	 } else if (selectedXShape instanceof XRect) {
    		 description = "Rectangle";
    	 } else if (selectedXShape instanceof XCircle) {
    		 description = "Circle";
    	 } else if (selectedXShape instanceof XEllipse) {
    		 description = "Ellipse";
    	 } else if (selectedXShape instanceof XHexagon) {
    		 description = "Hexagon";
    	 }
	     
	     System.out.println(selectedXShape.getCoordinates());

	     // Speaks the given text 
	     // until the queue is empty. 
	     synthesizer.speakPlainText( 
	         description + " " + selectedXShape.getTTSCoordinates(), null); 
	     synthesizer.waitEngineState( 
	         Synthesizer.QUEUE_EMPTY); 
	 } 

	 catch (Exception exception) { 
		   exception.printStackTrace(); 
	 } 
 }



}
