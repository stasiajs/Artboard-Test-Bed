package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
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

// TODO: Auto-generated Javadoc
/**
 * The Class GuiDelegate.
 */
public class GuiDelegate implements Observer {

 /** The model. */
 private Model model;

 /** The j frame. */
 private JFrame jFrame;

 /** The border pane. */
 private Container borderPane;

 /** The j menu bar. */
 private JMenuBar jMenuBar;

 /** The j tool bar. */
 private JToolBar jToolBar;

 /** The draw panel. */
 private DrawPanel drawPanel;

 /** The left bar. */
 private JToolBar leftBar;

 /** The down bar. */
 private JToolBar downBar;

 /** The j color chooser. */
 private JColorChooser jColorChooser;

 /**
  * Instantiates a new gui delegate.
  *
  * @param model the model
  */
 public GuiDelegate(Model model) {
  this.model = model;
  jFrame = new JFrame("Vectors");
  jMenuBar = new JMenuBar();
  jToolBar = new JToolBar();
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
  setupToolBar();
  setupLeftBar();
  setupDownBar();

  borderPane = jFrame.getContentPane();
  borderPane.setLayout(new BorderLayout());

  borderPane.add(jToolBar, BorderLayout.NORTH);
  borderPane.add(drawPanel, BorderLayout.CENTER);
  borderPane.add(leftBar, BorderLayout.WEST);
  borderPane.add(downBar, BorderLayout.SOUTH);
  //  borderPane.add(jColorChooser.getPreviewPanel(), BorderLayout.SOUTH);

  jFrame.setSize(800, 600);
  jFrame.setVisible(true);
  jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 }

 /**
  * Setup menu.
  */
 private void setupMenu() {
  JMenu file = new JMenu("File");
  JMenu edit = new JMenu("Edit");
  JMenuItem newProject = new JMenuItem("New Project");
  JMenuItem load = new JMenuItem("Load Project from File");
  JMenuItem save = new JMenuItem("Save Project as...");

  JMenuItem importImage = new JMenuItem("Import Image");
  JMenuItem undo = new JMenuItem("Undo step");
  JMenuItem redo = new JMenuItem("Redo step");
  
  file.add(newProject);
  file.add(load);
  file.add(save);
  file.add(importImage);
  edit.add(undo);
  edit.add(redo);
  jMenuBar.add(file);
  jMenuBar.add(edit);
  
  newProject.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    model.clearUndoRedo();
    model.getShapeList().clear();
    model.notifyObservers();
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

//    drawPanel.setShapeList(model.getShapeList());
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
      model.addUndoAction();
      model.addShape(ximage);
      drawPanel.setShapeList(model.getShapeList());
      model.notifyObservers();
      drawPanel.repaint();
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
  * Setup tool bar.
  */
 private void setupToolBar() {

  JLabel label = new JLabel("Toolbar: ");

  jToolBar.add(label);
 }

 /**
  * Setup left bar.
  */
 private void setupLeftBar() {

  leftBar.setLayout(new BoxLayout(leftBar, BoxLayout.Y_AXIS));

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
    if (drawPanel.getSelectedXShape() != null && !drawPanel.getSelectedXShape().equals(null)) {
     model.addUndoAction();
     model.getShapeList().remove(drawPanel.getSelectedXShape());
     model.notifyObservers();
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
    model.addUndoAction();
    model.getShapeList().clear();
    model.notifyObservers();
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
  * Setup down bar.
  */
 private void setupDownBar() {
  JLabel label = new JLabel("Select color: ");
  JButton colorButton = new JButton();
  JButton fillButton = new JButton("Fill/unfill selected shape");

  colorButton.setSize(32, 32);
  colorButton.setBackground(Color.BLACK);
  colorButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    Color col = JColorChooser.showDialog(jColorChooser, "Choose Color", drawPanel.getColor());
    drawPanel.setColor(col);
    if (drawPanel.getSelectedXShape() != null) {
     model.addUndoAction();
     drawPanel.getSelectedXShape().setColor(col);
     drawPanel.repaint();
    }
    colorButton.setBackground(drawPanel.getColor());
   }
  });

  fillButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    if (drawPanel.getSelectedXShape() != null && !drawPanel.getSelectedXShape().equals(null)) {
     drawPanel.getSelectedXShape().setFill(!drawPanel.getSelectedXShape().isFill());
     drawPanel.repaint();
    }
   }
  });

  downBar.add(label);
  downBar.add(colorButton);
  downBar.addSeparator();
  downBar.add(fillButton);

 }

 /* (non-Javadoc)
  * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
  */
 @Override
 public void update(Observable o, Object arg) {

  SwingUtilities.invokeLater(new Runnable() {
   @Override
   public void run() {
    drawPanel.setShapeList(model.getShapeList());
    drawPanel.repaint();
   }
  });
 }

}
