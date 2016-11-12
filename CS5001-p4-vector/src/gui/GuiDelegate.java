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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
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
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

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
  borderPane.add(leftBar, BorderLayout.WEST);
  borderPane.add(downBar, BorderLayout.SOUTH);

  jFrame.setSize(Config.PANEL_WIDTH, Config.PANEL_HEIGHT);
  jFrame.setVisible(true);
  jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 }

 /**
  * Setup menu of the application.
  */
 private void setupMenu() {
  JMenu file = new JMenu("File");
  JMenu edit = new JMenu("Edit");
  JMenuItem newProject = new JMenuItem("New Project");
  JMenuItem load = new JMenuItem("Load Project from File");
  JMenuItem save = new JMenuItem("Save Project as...");
  JMenuItem export = new JMenuItem("Export to PNG");

  JMenuItem importImage = new JMenuItem("Import Image");
  undo = new JMenuItem("Undo step");
  redo = new JMenuItem("Redo step");

  file.add(newProject);
  file.add(load);
  file.add(save);
  file.addSeparator();
  file.add(importImage);
  file.add(export);
  edit.add(undo);
  edit.add(redo);
  jMenuBar.add(file);
  jMenuBar.add(edit);

  undo.setEnabled(false);
  redo.setEnabled(false);

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
     }
     catch (Exception e1) {
      JOptionPane.showMessageDialog(new JFrame(), "Could not load file!", "Error", JOptionPane.ERROR_MESSAGE);
     }
    }

    model.notifyObservers();
    drawPanel.repaint();

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
  jFrame.setJMenuBar(jMenuBar);
 }

 /**
  * Setup left bar of the application.
  */
 private void setupLeftBar() {

  leftBar.setLayout(new GridLayout(Config.MAX_LINES, 1));

  JLabel modeLabel = new JLabel("Mode: ");
  JButton drawButton = new JButton("Draw Mode");
  JButton selectButton = new JButton("Select Mode");

  JLabel label = new JLabel("Shapes: ");
  JButton lineButton = new JButton("Line");
  JButton squareButton = new JButton("Square");
  JButton rectButton = new JButton("Rectangle");
  JButton circleButton = new JButton("Circle");
  JButton ellipseButton = new JButton("Ellipse");
  JButton hexButton = new JButton("Hexagon");
  JButton deleteButton = new JButton("Delete Shape");
  JLabel actionLabel = new JLabel("Actions: ");
  JButton deleteAll = new JButton("Delete All");
  JCheckBox fillBox = new JCheckBox("Enable/Disable fill");

  drawButton.setEnabled(false);

  drawButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setMode(Config.DRAW_MODE);
    drawPanel.setSelectedXShape(null);
    drawPanel.repaint();
    drawButton.setEnabled(false);
    selectButton.setEnabled(true);
   }
  });

  selectButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setMode(Config.SELECT_MODE);
    selectButton.setEnabled(false);
    drawButton.setEnabled(true);
   }
  });

  lineButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_LINE);
   }
  });

  squareButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_SQUARE);
   }
  });

  rectButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_RECT);
   }
  });

  circleButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_CIRCLE);
   }
  });

  ellipseButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_ELLIPSE);
   }
  });

  hexButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setDrawMode(Config.DRAW_HEX);
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

  leftBar.add(modeLabel);
  leftBar.add(drawButton);
  leftBar.add(selectButton);
  leftBar.addSeparator();
  leftBar.add(fillBox);
  leftBar.addSeparator();
  leftBar.add(label);
  leftBar.add(lineButton);
  leftBar.add(squareButton);
  leftBar.add(rectButton);
  leftBar.add(circleButton);
  leftBar.add(ellipseButton);
  leftBar.add(hexButton);
  leftBar.addSeparator();
  leftBar.add(actionLabel);
  leftBar.add(deleteButton);
  leftBar.add(deleteAll);

 }

 /**
  * Setup bottom bar of the application.
  */
 private void setupDownBar() {
  JLabel label = new JLabel("Select color: ");
  JButton colorButton = new JButton();
  JButton fillButton = new JButton("Fill/unfill selected shape");

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

  downBar.add(label);
  downBar.add(colorButton);
  downBar.addSeparator();
  downBar.add(fillButton);

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

}
