package main;

import gui.GuiDelegate;
import model.Model;

public class Main {

 public static void main(String[] args) {
  Model model = new Model();
  GuiDelegate guiDelegate = new GuiDelegate(model);
 }
}
