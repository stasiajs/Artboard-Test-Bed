package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import shapes.XShape;

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

 public void saveToFile(String filename) {
  try {
   FileOutputStream fos = new FileOutputStream(filename);
   ObjectOutputStream oos = new ObjectOutputStream(fos);
   oos.writeObject(shapeList);
   oos.close();
   fos.close();
  }
  catch (Exception e) {
   System.out.println("File could not be written.");
  }
 }

 public void readFromFile(String filename) {
  try {
   FileInputStream fis = new FileInputStream(filename);
//   fis.
   ObjectInputStream ois = new ObjectInputStream(fis);
   ArrayList<XShape> readList = (ArrayList<XShape>) ois.readObject();
   shapeList = readList;
   ois.close();
   fis.close();

  }
  catch (Exception e) {
   System.out.println(e.getMessage());
  }
 }

}
