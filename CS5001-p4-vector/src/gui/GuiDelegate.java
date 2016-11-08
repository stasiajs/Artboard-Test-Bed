package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

public class GuiDelegate implements Observer {
 private Model model;

 private JFrame jFrame;
 private Container borderPane;
 private JMenuBar jMenuBar;
 private JToolBar jToolBar;
 private DrawPanel drawPanel;
 private JToolBar leftBar;
 private JToolBar downBar;
 private JColorChooser jColorChooser;

 public GuiDelegate(Model model) {
  this.model = model;
  jFrame = new JFrame("Vactorz");
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
  jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

 }

 private void setupMenu() {
  JMenu file = new JMenu("File");
  JMenu edit = new JMenu("Edit");
  JMenuItem load = new JMenuItem("Load");
  JMenuItem save = new JMenuItem("Save");
  JMenuItem importImage = new JMenuItem("Import Image");
  JMenuItem undo = new JMenuItem("Undo");
  JMenuItem redo = new JMenuItem("Redo");
  
  file.add(load);
  file.add(save);
  file.add(importImage);
  edit.add(undo);
  edit.add(redo);
  jMenuBar.add(file);
  jMenuBar.add(edit);

  load.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    int returnVal = fc.showOpenDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {

     try {
      File file = fc.getSelectedFile();
      model.readFromFile(file.toString());
      System.out.println("File is " + file.toString());
     }
     catch (Exception e1) {
     }
    }
    else {
     System.out.println("User didn't select and Ok file choice");
    }
    
//    model.readFromFile("file");
    // do i need this or is notify enough?
    drawPanel.setShapeList(model.getShapeList());
    model.notifyObservers();
    drawPanel.repaint();
    
   }
  });
  save.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    
    JFileChooser fc = new JFileChooser();
    int returnVal = fc.showSaveDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {

      try {
       
       File file = fc.getSelectedFile();
       model.saveToFile(file.toString());
        System.out.println ("File is " + file.toString());
      } catch (Exception e1) {}
    } else {
      System.out.println("User didn't select and Ok file choice");
    }
    
//    model.saveToFile("file");
    drawPanel.repaint();
   }
  });
  
  
  importImage.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    BufferedImage image = null;
    int returnVal = fc.showOpenDialog(fc);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
     
     try {
      File file = fc.getSelectedFile();
      image = ImageIO.read(file);
      

      

      
      System.out.println("File is " + file.toString());
     }
     catch (Exception e1) {
      System.out.println("batz");
      e1.getMessage();
     }
    }
    else {
     System.out.println("User didn't select and Ok file choice");
    }
    XImage ximage = new XImage(image, 0, 0);
    model.addUndoAction();
    
    model.addShape(ximage);
    
    drawPanel.setShapeList(model.getShapeList());
    model.notifyObservers();
    drawPanel.repaint();
    
    
   }
  });
  
  undo.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    drawPanel.setSelectedCShape(null);
    model.undo();
   }
  });
  redo.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    drawPanel.setSelectedCShape(null);
    model.redo();
   }
  });
  jFrame.setJMenuBar(jMenuBar);
 }

 private void setupToolBar() {

  JLabel label = new JLabel("Toolbar: ");

  jToolBar.add(label);
 }

 private void setupLeftBar() {

  leftBar.setLayout(new BoxLayout(leftBar, BoxLayout.Y_AXIS));

  JLabel modeLabel = new JLabel("Mode: ");
  JButton drawButton = new JButton("Draw Mode");
  JButton selectButton = new JButton("Select Mode");

  JLabel label = new JLabel("SideBar: ");
  JButton lineButton = new JButton("Line");
  JButton squareButton = new JButton("Square");
  JButton rectButton = new JButton("Rectangle");
  JButton circleButton = new JButton("Circle");
  JButton ellipseButton = new JButton("Ellipse");
  JButton hexButton = new JButton("Hexagon");
  JButton orientationButton = new JButton("Change orientation");

  drawButton.setEnabled(false);

  drawButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    drawPanel.setMode(Config.DRAW_MODE);
    drawPanel.setSelectedCShape(null);
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

  leftBar.add(modeLabel);
  leftBar.add(drawButton);
  leftBar.add(selectButton);

  leftBar.add(label);
  leftBar.add(lineButton);
  leftBar.add(squareButton);
  leftBar.add(rectButton);
  leftBar.add(circleButton);
  leftBar.add(ellipseButton);
  leftBar.add(hexButton);

  leftBar.addSeparator();
  leftBar.add(orientationButton);

 }

 private void setupDownBar() {
  JLabel label = new JLabel("DownBar: ");
  JButton colorButton = new JButton();

  colorButton.setSize(32, 32);
  colorButton.setBackground(Color.BLACK);
  colorButton.addActionListener(new ActionListener() {

   @Override
   public void actionPerformed(ActionEvent e) {
    Color col = JColorChooser.showDialog(jColorChooser, "Choose Color", drawPanel.getColor());
    drawPanel.setColor(col);
    if (drawPanel.getSelectedCShape() != null) {
     model.addUndoAction();
     drawPanel.getSelectedCShape().setColor(col);
     drawPanel.repaint();
    }
    colorButton.setBackground(drawPanel.getColor());
   }
  });

  downBar.add(label);
  downBar.add(colorButton);

 }

 public void update(Observable o, Object arg) {

  SwingUtilities.invokeLater(new Runnable() {
   public void run() {
    drawPanel.setShapeList(model.getShapeList());
    drawPanel.repaint();
   }
  });
 }

}
