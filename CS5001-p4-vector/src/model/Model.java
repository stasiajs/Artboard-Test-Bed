package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import shapes.CShape;
import sun.security.provider.SHA;

public class Model extends Observable {

 private ArrayList<CShape> shapeList;
 private Stack<CShape> redoable;
 private Color currentColor;
 private int lineStrength;

 public Model() {
  shapeList = new ArrayList<CShape>();
  redoable = new Stack<CShape>();

  currentColor = Color.BLACK;
  lineStrength = 1;

 }

 public void undo() {
  if (shapeList.size() > 0) {
   redoable.push(shapeList.remove(shapeList.size() - 1));
   setChanged();
   notifyObservers();
  }
 }

 public void redo() {
  if (redoable.size() > 0) {
   shapeList.add(redoable.pop());
   setChanged();
   notifyObservers();
  }
 }

 public void addShape(CShape cshape) {
  redoable.removeAllElements();
  shapeList.add(cshape);
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
