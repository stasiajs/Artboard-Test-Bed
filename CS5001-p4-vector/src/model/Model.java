package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;

import shapes.CShape;
import sun.security.provider.SHA;

public class Model extends Observable {

 private ArrayList<CShape> shapeList;
 private Color currentColor;
 private int lineStrength;

 private int undoPos;

 public Model() {
  shapeList = new ArrayList<CShape>();
  undoPos = 0;
  currentColor = Color.BLACK;
  lineStrength = 1;
 }

 public void undo() {

  if (undoPos >= 0 && shapeList.size() > 0) {
   shapeList.get(undoPos).setVisible(false);
   setChanged();
   notifyObservers();
   
   System.out.println("undo before: "+undoPos);
   
   if (undoPos > 0) {
    undoPos--;
   }

  }

 }

 public void redo() {
  if (undoPos < shapeList.size()) {
   
   if (undoPos < shapeList.size() - 1) {
    undoPos++;
   }
   
   shapeList.get(undoPos).setVisible(true);
   setChanged();
   notifyObservers();
   
   System.out.println("redo before: "+undoPos);



  }
 }

 public void addShape(CShape cshape) {
  for (int i = shapeList.size() - 1; i > undoPos; i--) {
   shapeList.remove(i);
  }
  shapeList.add(cshape);
  undoPos = shapeList.size() - 1;
  setChanged();
  notifyObservers();
 }

 public ArrayList<CShape> getShapeList() {
  return shapeList;
 }

 // public void setShapeList(ArrayList<CShape> shapeList) {
 //  shapeList = shapeList;
 //  setChanged();
 //  notifyObservers();
 // }

 public Color getCurrentColor() {
  return currentColor;
 }

 public void setCurrentColor(Color currentColor) {
  this.currentColor = currentColor;
  setChanged();
  notifyObservers();
 }

 public int getLineStrength() {
  return lineStrength;
 }

 public void setLineStrength(int lineStrength) {
  this.lineStrength = lineStrength;
  setChanged();
  notifyObservers();
 }

}
