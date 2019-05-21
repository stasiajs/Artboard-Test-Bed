package main;

import gui.GuiDelegate;
import model.Model;

/**
 * The Main class generates a new GuiDelegate and a new Model, that the GuiDelegate can see.
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
