package main;

import gui.GuiDelegate;
import model.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {

 /**
  * The main method.
  *
  * @param args the arguments
  */
 public static void main(String[] args) {
  Model model = new Model();
  GuiDelegate guiDelegate = new GuiDelegate(model);
 }
}
