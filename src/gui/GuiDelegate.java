package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.accessibility.*;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

import model.Model;
import shapes.XCircle;
import shapes.XEllipse;
import shapes.XImage;
import shapes.XLine;
import shapes.XRect;
import shapes.XShape;
import shapes.XSquare;
import shapes.XText;
import shapes.XTriangle;

/**
 * The Class GuiDelegate describes the GUI elements of the application. 
 * It has a menu bar for file and edit operations, a sidebar for drawing operation, 
 * a bottom bar for selecting color and fill for shapes, and a JPanel for drawing.
 */
public class GuiDelegate implements Observer {

 /** The model, that can be seen by the GUI. */
 private Model model;

 /** The main JFrame. */
 private JFrame jFrame;

 /** A border pane for the layout. */
 private Container borderPane;

 /** The menu bar of the application. */
 private JMenuBar jMenuBar;

 /** The draw panel, where the shapes are drawn on. */
 private DrawPanel drawPanel;

 /** The left bar has different buttons for shape operations. */
 private JToolBar leftBar;

 /** The down bar has buttons for choosing color and fill. */
 private JToolBar downBar;

 /** The color chooser. */
 private JColorChooser jColorChooser;

 /** The undo menu item. */
 private JMenuItem undo;

 /** The redo menu item. */
 private JMenuItem redo;
 
 private JButton deleteButton;
 
 private JButton fillButton;
 
 private JLabel borderLabel;
 
 private JTextField borderButton;
 
 private JLabel fontLabel;
 
 private JTextField fontField;

 /** Booleans for custom settings */
 private boolean defaultSettingsOn = true;
 
 private XShape loadedShape;
 
 private boolean drawDefaultOnClick = false;
 
 /**
  * Instantiates a new gui delegate.
  *
  * @param model the model that the delegate can see
  */
 public GuiDelegate(Model model) {
  this.model = model;
  jFrame = new JFrame("Vectors");
  jMenuBar = new JMenuBar();
  leftBar = new JToolBar();
  jMenuBar = new JMenuBar();
  drawPanel = new DrawPanel(model);
  jColorChooser = new JColorChooser();
  downBar = new JToolBar();
  
  setupComponents();
  model.addObserver(this);
  drawPanel.setFocusable(true);
  
  drawPanel.setLayout(null);
 }

 /**
  * Setup components.
  */
 private void setupComponents() {
  setupMenu();
  setupLeftBar();
  setupDownBar();

  borderPane = jFrame.getContentPane();
  borderPane.setLayout(new BorderLayout());

  borderPane.add(drawPanel, BorderLayout.CENTER);
  borderPane.add(leftBar, BorderLayout.PAGE_START);
  borderPane.add(downBar, BorderLayout.SOUTH);
  

  jFrame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
  jFrame.setVisible(true);
  jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  /*
  drawPanel.addKeyListener(new KeyListener() {
	  
	   @Override
	   public void keyReleased(KeyEvent ke) {
		   /*if(ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE)
		    {  
				System.out.println("Backspace pressed");

				if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
				     model.removeShape(drawPanel.getSelectedXShape());
				     drawPanel.setSelectedXShape(null);
				     drawPanel.repaint();
				    }
		    }
		   
	   }


		@Override
		public void keyPressed(KeyEvent ke) {
			int keyCode = ke.getExtendedKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
	            // handle up 
				System.out.println("Up arrow key pressed");
	            break;
	        case KeyEvent.VK_DOWN:
	            // handle down 
	        	System.out.println("Down arrow key pressed");
	            break;
	        case KeyEvent.VK_LEFT:
	            // handle left
	        	System.out.println("Left arrow key pressed");
	            break;
	        case KeyEvent.VK_RIGHT :
	            // handle right
	        	System.out.println("Right arrow key pressed");
	            break;
	        default:
	        	break;
			}
		}

		@Override
		public void keyTyped(KeyEvent ke) {
			
		}
}); */
 }

 /**
  * Setup menu of the application.
  */
 private void setupMenu() {
  JMenu file = new JMenu("File");
  JMenu edit = new JMenu("Edit");
  JMenu customize = new JMenu("Customize");
  JMenuItem newProject = new JMenuItem("New Project");
  JMenuItem load = new JMenuItem("Load Project from File");
  JMenuItem save = new JMenuItem("Save Project as...");
  JMenuItem export = new JMenuItem("Export to PNG");

  JMenuItem importImage = new JMenuItem("Import Image");
  undo = new JMenuItem("Undo step");
  redo = new JMenuItem("Redo step");
  
  JCheckBox defaultSettings = new JCheckBox("Default");
  
  // Interaction mode checklist
  // JMenu interact = new JMenu("Interaction Mode");
  //JMenuItem mouse = new JMenuItem("Mouse navigation");
  //JMenuItem keyboard = new JMenuItem("Keyboard navigation");

  file.add(newProject);
  file.add(load);
  file.add(save);
  file.addSeparator();
  file.add(importImage);
  file.add(export);
  edit.add(undo);
  edit.add(redo);
  customize.add(defaultSettings);
  jMenuBar.add(file);
  jMenuBar.add(edit);
  jMenuBar.add(customize);

  undo.setEnabled(false);
  redo.setEnabled(false);
  defaultSettings.setSelected(true);

  
  /*
   * File
   */
  newProject.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    model.clearUndoRedo();
    model.getShapeList().clear();
    model.notifyObservers();
    drawPanel.setSelectedXShape(null);
    drawPanel.repaint();
   }
  });

  load.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    int returnVal = fc.showOpenDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {

     try {
      File file = fc.getSelectedFile();
      model.readFromFile(file.toString());
      model.clearUndoRedo();
      model.notifyObservers();
      drawPanel.setSelectedXShape(null);
      drawPanel.repaint();
     }
     catch (Exception e1) {
      JOptionPane.showMessageDialog(new JFrame(), "Could not load file!", "Error", JOptionPane.ERROR_MESSAGE);
     }
    }

   }
  });
  save.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {

    JFileChooser fc = new JFileChooser();
    int returnVal = fc.showSaveDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {

     try {

      File file = fc.getSelectedFile();
      model.saveToFile(file.toString());
     }
     catch (Exception e1) {
      JOptionPane.showMessageDialog(new JFrame(), "Could not save file!", "Error", JOptionPane.ERROR_MESSAGE);

     }
    }

    drawPanel.repaint();
   }
  });

  export.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    int returnVal = fc.showSaveDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {

     try {
      BufferedImage exportImg = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(),
        BufferedImage.TYPE_INT_RGB);
      Graphics2D exportGraphics = exportImg.createGraphics();
      drawPanel.setExporting(true);
      drawPanel.paintComponent(exportGraphics);
      File file = fc.getSelectedFile();
      ImageIO.write(exportImg, "png", file);
      drawPanel.setExporting(false);
     }
     catch (IOException e1) {
      JOptionPane.showMessageDialog(new JFrame(), "Could not save file!", "Error", JOptionPane.ERROR_MESSAGE);
     }
    }
   }
  });

  importImage.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    BufferedImage image = null;
    int returnVal = fc.showOpenDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {

     try {
      File file = fc.getSelectedFile();
      image = ImageIO.read(file);
      XImage ximage = new XImage(image, 0, 0);
      model.addShape(ximage);
     }
     catch (Exception e1) {
      JOptionPane.showMessageDialog(new JFrame(), "Could not load file!", "Error", JOptionPane.ERROR_MESSAGE);
     }
    }
   }
  });
  
  /*
   * Edit
   */

  undo.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setSelectedXShape(null);
    model.undo();
   }
  });
  redo.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setSelectedXShape(null);
    model.redo();
   }
  });
  
  /*
   * Customize
   */
  defaultSettings.addActionListener(new ActionListener() {
	   @Override
	   public void actionPerformed(ActionEvent e) {
	    if (defaultSettingsOn) {
	    	defaultSettingsOn = false;
	    }
	    else {
	    	defaultSettingsOn = true;
	    }
	    
	    System.out.println(defaultSettingsOn);
	   }
	  });
  
  jFrame.setJMenuBar(jMenuBar);
 }

 /**
  * Setup left bar of the application.
  */
 private void setupLeftBar() {

  // Make all buttons able to be selected by pressing Enter
  setupEnterAction("Button");
  setupEnterAction("RadioButton");
  setupEnterAction("CheckBox");
  
  //leftBar.setLayout(new GridLayout(Config.MAX_LINES, 1));
  leftBar.setLayout(new GridLayout(2, 12));

  // User should be able to enter select mode by pressing Alt+S
  // and enter draw mode by pressing Alt+D
  // This could be just one toggle button but this seemed easiest
  //JLabel modeLabel = new JLabel("Mode: ");
  //JRadioButton selectButton = new JRadioButton("Select Mode");
  //selectButton.setMnemonic(KeyEvent.VK_S);
  //JRadioButton drawButton = new JRadioButton("Draw Mode");
  //drawButton.setMnemonic(KeyEvent.VK_D);


  //ButtonGroup modeButtonGroup = new ButtonGroup();
  //modeButtonGroup.add(selectButton);
  //modeButtonGroup.add(drawButton);
  

  JLabel label = new JLabel("Shapes: ");
  JButton lineButton = new JButton("Line");
  JButton triangleButton = new JButton("Triangle");
  JButton squareButton = new JButton("Square");
  JButton rectButton = new JButton("Rectangle");
  JButton circleButton = new JButton("Circle");
  JButton ellipseButton = new JButton("Ellipse");
  JButton regionButton = new JButton("Region");
  JButton textButton = new JButton("Text");
  deleteButton = new JButton("Delete Shape");
  JButton deleteAll = new JButton("Delete All");
  JCheckBox fillBox = new JCheckBox("Enable/Disable fill");
  

  // TODO: press Enter or something else to add default size shape to canvas
  // TODO: this is only for the mode that's add-shape-on-click
  lineButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  triangleButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  squareButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  rectButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  circleButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  ellipseButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  regionButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  textButton.getAccessibleContext().setAccessibleDescription("Press Enter to place text.");
  deleteAll.getAccessibleContext().setAccessibleDescription("Press Enter to delete all shapes.");

  drawPanel.setMode(Config.SELECT_MODE);
  
  lineButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    if (drawDefaultOnClick) {
    	drawPanel.setMode(Config.DRAW_MODE);
    	drawPanel.setDrawMode(Config.DRAW_LINE);
        drawPanel.drawDefaultSelectedShape();
        
        drawPanel.setMode(Config.SELECT_MODE);
        loadedShape = null;
    } else {
    	loadedShape = new XLine();
    }
	
   }
  });
  
  triangleButton.addActionListener(new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
		  if (drawDefaultOnClick) {
			  drawPanel.setMode(Config.DRAW_MODE);
			  drawPanel.setDrawMode(Config.DRAW_TRIANGLE);
			  drawPanel.drawDefaultSelectedShape();
			  
			  drawPanel.setMode(Config.SELECT_MODE);
		  } else {
		    	loadedShape = new XTriangle();
		    }
	  }
	  
  });
  
  squareButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
	   if (drawDefaultOnClick) {
			drawPanel.setMode(Config.DRAW_MODE);
			drawPanel.setDrawMode(Config.DRAW_SQUARE);
		    drawPanel.drawDefaultSelectedShape();
		    
		    drawPanel.setMode(Config.SELECT_MODE);
	   } else {
	    	loadedShape = new XSquare();
	    }
   }
  });

  rectButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
	   if (drawDefaultOnClick) {
		drawPanel.setMode(Config.DRAW_MODE);
	    drawPanel.setDrawMode(Config.DRAW_RECT);
	    drawPanel.drawDefaultSelectedShape();
	    
	    drawPanel.setMode(Config.SELECT_MODE);
	   } else {
	    	loadedShape = new XRect();
	    }
   }
  });

  circleButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
	   if (drawDefaultOnClick) {
		drawPanel.setMode(Config.DRAW_MODE);
		drawPanel.setDrawMode(Config.DRAW_CIRCLE);
	    drawPanel.drawDefaultSelectedShape();
	    
	    drawPanel.setMode(Config.SELECT_MODE);
	   } else {
	    	loadedShape = new XCircle();
	    }
   }
  });

  ellipseButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
	   if (drawDefaultOnClick) {
		drawPanel.setMode(Config.DRAW_MODE);
		drawPanel.setDrawMode(Config.DRAW_ELLIPSE);
	    drawPanel.drawDefaultSelectedShape();
	    
	    drawPanel.setMode(Config.SELECT_MODE);
	   } else {
	    	loadedShape = new XEllipse();
	    }
   }
  });

  regionButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
	if (drawDefaultOnClick) {
		drawPanel.setMode(Config.DRAW_MODE);
		drawPanel.setDrawMode(Config.DRAW_HEX);
	    drawPanel.drawDefaultSelectedShape();
	    
	    drawPanel.setMode(Config.SELECT_MODE);
	} else {
    	//loadedShape = new XRegion();
    }
   }
  });
  
  textButton.addActionListener(new ActionListener() {

	   @Override
	   public void actionPerformed(ActionEvent e) {
		if (drawDefaultOnClick) {
			drawPanel.setMode(Config.DRAW_MODE);
			drawPanel.setDrawMode(Config.DRAW_HEX);
		    drawPanel.drawDefaultSelectedShape();
		    
		    drawPanel.setMode(Config.SELECT_MODE);
		} else {
	    	loadedShape = new XText();
	    }
	   }
	  });
  
  deleteButton.addActionListener(new ActionListener() {
	  @Override
	   public void actionPerformed(ActionEvent e) {
		  if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
			model.removeShape(drawPanel.getSelectedXShape());
			drawPanel.setSelectedXShape(null);
			drawPanel.repaint();
		}
	  }
});
  
  
  fillBox.addItemListener(new ItemListener() {
   @Override
   public void itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == ItemEvent.SELECTED) {
     drawPanel.setSetFill(true);
    }
    else {
     drawPanel.setSetFill(false);
    }
   }
  });

  deleteAll.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    model.clearShapeList();
    drawPanel.setSelectedXShape(null);
    drawPanel.repaint();
   }
  });
  
  
  
  /* ACTIONS */
  /*
  Action delete = new AbstractAction() {
	  public void actionPerformed(ActionEvent e) {
		  if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
				model.removeShape(drawPanel.getSelectedXShape());
				drawPanel.setSelectedXShape(null);
				drawPanel.repaint();
			}
	  }
  };
  
  Action moveUpAction = new AbstractAction() {
	  public void actionPerformed(ActionEvent e) {
		  //if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
				System.out.println("Move up");
			//}
	  }
  };
  */
  
  Action getNextShapeAction = new AbstractAction() {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
			System.out.println("Tab hit");
		}
	}
	  
  };
  
  /* KEY BINDINGS */
  
  // delete
  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
  drawPanel.getActionMap().put("delete", new Delete(drawPanel));
  
  // move shapes with W, A, S, D (for now)
  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "move up");;
  drawPanel.getActionMap().put("move up", new Move(drawPanel, 0));
  
  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "move down");;
  drawPanel.getActionMap().put("move down", new Move(drawPanel, 1));
  
  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "move left");;
  drawPanel.getActionMap().put("move left", new Move(drawPanel, 3));
  
  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "move right");;
  drawPanel.getActionMap().put("move right", new Move(drawPanel, 4));
  
  // tab traversal of shapes
  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "next shape");
  drawPanel.getActionMap().put("next shape", getNextShapeAction);
  
  drawPanel.addMouseListener(new MouseListener() {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// see if a shape is loaded
		if (loadedShape != null) {
			// if it's text, create text input field where the mouse clicked
			if (loadedShape instanceof XText) {
				JTextPane textPane = new JTextPane();
				loadedShape.construct(arg0.getX(), arg0.getY(), arg0.getX() + 100, arg0.getY() + 100);
				((XText) loadedShape).setTextPane(textPane);
				drawPanel.add(textPane);
			} else {
				// deposit loaded shape to mouse coordinates
				loadedShape.construct(arg0.getX() - 100, arg0.getY() - 100, arg0.getX(), arg0.getY());
			}
			
			model.addShape(loadedShape);
			loadedShape = null;
		
		}
		
		if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
			deleteButton.setEnabled(true);
			fillButton.setEnabled(true);
			
			if (drawPanel.getSelectedXShape() instanceof XText) {
				fontLabel.setEnabled(true);
				fontField.setEnabled(true);
				
				borderLabel.setEnabled(false);
				borderButton.setEnabled(false);
			} else {
				fontLabel.setEnabled(false);
				fontField.setEnabled(false);
				
				borderLabel.setEnabled(true);
				borderButton.setEnabled(true);
			}
		} 
		else {
			deleteButton.setEnabled(false);
			fillButton.setEnabled(false);
			fontLabel.setEnabled(false);
			fontField.setEnabled(false);
			borderLabel.setEnabled(false);
			borderButton.setEnabled(false);
		
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
  
  });
  
  leftBar.add(lineButton);
  leftBar.add(triangleButton);
  leftBar.add(squareButton);
  leftBar.add(rectButton);
  leftBar.add(circleButton);
  leftBar.add(ellipseButton);
  leftBar.add(regionButton);
  leftBar.add(textButton);
  leftBar.add(deleteButton);
  leftBar.add(deleteAll);
  
  deleteButton.setEnabled(false);
  deleteAll.setEnabled(false);
 }

 /**
  * Setup bottom bar of the application.
  */
 private void setupDownBar() {
  JLabel colorLabel = new JLabel("Select color: ");
  JButton colorButton = new JButton();
  fillButton = new JButton("Fill/unfill selected shape");
  
  borderLabel = new JLabel("Input border thickness: ");
  borderButton = new JTextField(1);
  
  fontLabel = new JLabel("Input font size: ");
  fontField = new JTextField(1);
  
  //JCheckBox modeBox = new JCheckBox("Enable drawing or use Alt+D to toggle");
  // use Alt+D to enable/disable draw mode
  //modeBox.setMnemonic(KeyEvent.VK_D);
  //modeBox.setFocusable(false);

  colorButton.setBackground(Color.BLACK);
  colorButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    Color col = JColorChooser.showDialog(jColorChooser, "Choose Color", drawPanel.getColor());
    drawPanel.setColor(col);
    if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
     model.setColor(drawPanel.getSelectedXShape(), col);
    }
    colorButton.setBackground(drawPanel.getColor());
   }
  });

  fillButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
     model.setFill(drawPanel.getSelectedXShape(), !drawPanel.getSelectedXShape().isFill());
    }
   }
  });
  
  borderButton.addActionListener(new ActionListener() {

	   @Override
	   public void actionPerformed(ActionEvent e) {
	    if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
	    	try {
	    		int newBorder = Integer.parseInt(borderButton.getText());
	    		model.setBorder(drawPanel.getSelectedXShape(), newBorder);
	    	} catch (NumberFormatException e1) {
	    		e1.printStackTrace();
	    	}
	    }
	   }
	  });
  
  fontField.addActionListener(new ActionListener() {

	   @Override
	   public void actionPerformed(ActionEvent e) {
	    if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
	    	try {
	    		int newFont = Integer.parseInt(fontField.getText());
	    		model.setFontSize(drawPanel.getSelectedXShape(), newFont);
	    	} catch (NumberFormatException e1) {
	    		e1.printStackTrace();
	    	}
	    }
	   }
	  });

  /*
  modeBox.addItemListener(new ItemListener() {

	   @Override
	   public void itemStateChanged(ItemEvent e) {
	    if (e.getStateChange() == ItemEvent.SELECTED) {
	    	drawPanel.setMode(Config.DRAW_MODE);
	        drawPanel.setSelectedXShape(null);
	        drawPanel.repaint();
	    }
	    else {
	    	drawPanel.setMode(Config.SELECT_MODE);
	    }
	   }
	  });
  */
  downBar.add(colorLabel);
  downBar.add(colorButton);
  downBar.addSeparator();
  downBar.add(fillButton);
  downBar.add(borderLabel);
  downBar.add(borderButton);
  downBar.add(fontLabel);
  downBar.add(fontField);;
  //downBar.add(modeBox);

  fillButton.setEnabled(false);
  borderLabel.setEnabled(false);
  borderButton.setEnabled(false);
  fontLabel.setEnabled(false);
  fontField.setEnabled(false);
 }

 /**
  * Updates the model reference and repaints the shapes.
  *
  * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
  */
 @Override
 public void update(Observable o, Object arg) {

  SwingUtilities.invokeLater(new Runnable() {
   @Override
   public void run() {
    drawPanel.setModel(model);
    drawPanel.repaint();

    // check if redo is possible and enable/disable the button
    if (model.redoIsEmpty()) {
     redo.setEnabled(false);
    }
    else {
     redo.setEnabled(true);
    }

    //check if undo is possible and enable/disable the button
    if (model.undoIsEmpty()) {
     undo.setEnabled(false);
    }
    else {
     undo.setEnabled(true);
    }
    
   }
  });
 }
 
private void setupEnterAction(String componentName){
	    String keyName = componentName + ".focusInputMap";
	    InputMap im = (InputMap) UIManager.getDefaults().get(keyName);
	    Object pressedAction = im.get(KeyStroke.getKeyStroke("pressed SPACE"));
	    Object releasedAction = im.get(KeyStroke.getKeyStroke("released SPACE"));
	    im.put(KeyStroke.getKeyStroke("pressed ENTER"), pressedAction);
	    im.put(KeyStroke.getKeyStroke("released ENTER"), releasedAction);
	}
}

class Delete extends AbstractAction {
	   DrawPanel drawPanel;
		
	   public Delete(DrawPanel dp) {
	      System.out.println("Delete created");
	      drawPanel = dp;
	   }

	   @Override
	   public void actionPerformed(ActionEvent arg0) {
	      System.out.println("Delete called");
	      if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
				drawPanel.getModel().removeShape(drawPanel.getSelectedXShape());
				drawPanel.setSelectedXShape(null);
				drawPanel.repaint();
			}
	   }
}

class Move extends AbstractAction {
	DrawPanel drawPanel;
	int direction;
	XShape selectedXShape;
	
	public Move(DrawPanel dp, int dir) {
		drawPanel = dp;
		direction = dir;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// 0 = up, 1 = down, 3 = left, 4 = right
		selectedXShape = drawPanel.getSelectedXShape();
		//System.out.println(selectedXShape.getCoordinates());
		
		//System.out.println("Direction: " + direction);
		 if ((selectedXShape != null) && !selectedXShape.equals(null)) {
			 int x = selectedXShape.getX1() + (selectedXShape.getX2() - selectedXShape.getX1())/2;
			 int y = selectedXShape.getY1() + (selectedXShape.getY2() - selectedXShape.getY1())/2;
			 System.out.println(x + ", " + y);
			 
			 drawPanel.setDrag(true);
			 
			 if (direction == 0) {
				 drawPanel.moveXShape(x, y - 1);
			 } else if (direction == 1) {
				 drawPanel.moveXShape(x, y + 1);
			 } else if (direction == 3) {
				 drawPanel.moveXShape(x - 1, y);
			 } else if (direction == 4) {
				 drawPanel.moveXShape(x + 1, y);
			 }
			 
			 drawPanel.repaint();
			 drawPanel.setDrag(false);
			}
	}
	
}
