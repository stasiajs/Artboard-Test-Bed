package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

import model.Model;
import shapes.XImage;

/**
 * The Class GuiDelegate describes the GUI elements of the application. It has a menu bar for file and edit operations, a sidebar for drawing operation, a bottom bar for selecting color and fill for shapes, and a JPanel for drawing.
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

 /** Booleans for custom settings */
 private boolean defaultSettingsOn = true;
 
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
  //borderPane.add(leftBar, BorderLayout.WEST);
  borderPane.add(leftBar, BorderLayout.PAGE_START);
  borderPane.add(downBar, BorderLayout.SOUTH);
  
  //drawPanel.setFocusable(true);
  //jFrame.setFocusable(true);

  jFrame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
  jFrame.setVisible(true);
  jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  drawPanel.addKeyListener(new KeyListener() {
	  
	   @Override
	   public void keyReleased(KeyEvent ke) {
		   if(ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE)
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
			
		}

		@Override
		public void keyTyped(KeyEvent ke) {
			
		}
});
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
  JButton squareButton = new JButton("Square");
  JButton rectButton = new JButton("Rectangle");
  JButton circleButton = new JButton("Circle");
  JButton ellipseButton = new JButton("Ellipse");
  JButton hexButton = new JButton("Hexagon");
  JButton deleteButton = new JButton("Delete Shape");
  //JLabel actionLabel = new JLabel("Actions (Select a shape in order to delete it): ");
  JButton deleteAll = new JButton("Delete All");
  JCheckBox fillBox = new JCheckBox("Enable/Disable fill");
  

  // TODO: press Enter or something else to add default size shape to canvas
  
  lineButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  squareButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  rectButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  circleButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  ellipseButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  hexButton.getAccessibleContext().setAccessibleDescription("Press Enter to place shape.");
  deleteAll.getAccessibleContext().setAccessibleDescription("Press Enter to delete all shapes.");

  drawPanel.setMode(Config.SELECT_MODE);
  
  lineButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_LINE);
    drawPanel.drawDefaultSelectedShape();
   }
  });
  
  squareButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_SQUARE);
    drawPanel.drawDefaultSelectedShape();
   }
  });

  rectButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_RECT);
    drawPanel.drawDefaultSelectedShape();
   }
  });

  circleButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_CIRCLE);
    drawPanel.drawDefaultSelectedShape();
   }
  });

  ellipseButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_ELLIPSE);
    drawPanel.drawDefaultSelectedShape();
   }
  });

  hexButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_HEX);
    drawPanel.drawDefaultSelectedShape();
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
  
  Action delete = new AbstractAction() {
	  public void actionPerformed(ActionEvent e) {
		  if ((drawPanel.getSelectedXShape() != null) && !drawPanel.getSelectedXShape().equals(null)) {
				model.removeShape(drawPanel.getSelectedXShape());
				drawPanel.setSelectedXShape(null);
				drawPanel.repaint();
			}
	  }
  };

  drawPanel.getInputMap(drawPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "backspace");
  drawPanel.getActionMap().put("backspace", delete);

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
  
  //leftBar.add(modeLabel);
  //leftBar.add(selectButton);
  //leftBar.add(drawButton);
  //leftBar.addSeparator();
  //leftBar.add(fillBox);
  //leftBar.addSeparator();
  //leftBar.add(label);
  leftBar.add(lineButton);
  leftBar.add(squareButton);
  leftBar.add(rectButton);
  leftBar.add(circleButton);
  leftBar.add(ellipseButton);
  leftBar.add(hexButton);
  leftBar.add(deleteButton);
  //deleteButton.setVisible(false);
  leftBar.add(deleteAll);
 }

 /**
  * Setup bottom bar of the application.
  */
 private void setupDownBar() {
  JLabel label = new JLabel("Select color: ");
  JButton colorButton = new JButton();
  JButton fillButton = new JButton("Fill/unfill selected shape");
  
  JCheckBox modeBox = new JCheckBox("Enable drawing or use Alt+D to toggle");
  // use Alt+D to enable/disable draw mode
  modeBox.setMnemonic(KeyEvent.VK_D);
  modeBox.setFocusable(false);

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
  
  downBar.add(label);
  downBar.add(colorButton);
  downBar.addSeparator();
  downBar.add(fillButton);
  downBar.add(modeBox);

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
