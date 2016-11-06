package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import shapes.XShape;
import sun.security.provider.SHA;

public class Model extends Observable {

 private ArrayList<XShape> shapeList;
 private Stack<XShape> redoable;

 public Model() {
  shapeList = new ArrayList<XShape>();
  redoable = new Stack<XShape>();


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

 public void addShape(XShape xshape) {
  redoable.removeAllElements();
  shapeList.add(xshape);
  setChanged();
  notifyObservers();
 }

 public ArrayList<XShape> getShapeList() {
  return shapeList;
 }

}
