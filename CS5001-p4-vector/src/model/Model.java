package model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import shapes.XShape;

public class Model extends Observable {

 private ArrayList<XShape> shapeList;
 private Stack<byte[]> redoStack;
 private Stack<byte[]> undoStack;

 public Model() {
  shapeList = new ArrayList<XShape>();
  redoStack = new Stack<byte[]>();
  undoStack = new Stack<byte[]>();

 }

 public void undo() {
  if (undoStack.size() > 0) {
   try {
    System.out.println("undoPeek: " + undoStack.peek());
    
    // put the current list on the redoStack
    redoStack.push(serialize(shapeList));
    
    // make the last item of the undoStack the list
    shapeList = new ArrayList<XShape>(deserialize((undoStack.pop())));
    
    
    

   }
   catch (ClassNotFoundException e) {
    e.printStackTrace();
   }
   catch (IOException e) {
    e.printStackTrace();
   }
   setChanged();
   notifyObservers();
  }
 }

 public void redo() {
  if (redoStack.size() > 0) {
   try {
    System.out.println("redoPeek: " + redoStack.peek());
    undoStack.push(serialize(shapeList));
    shapeList = deserialize(redoStack.pop());


   }
   catch (ClassNotFoundException e) {
    e.printStackTrace();
   }
   catch (IOException e) {
    e.printStackTrace();
   }
   setChanged();
   notifyObservers();
  }
 }

 public void addUndoAction(){
  
  redoStack.clear();
  
  try {
   // push current state
   undoStack.push(serialize(shapeList));
  }
  catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  System.out.println("undoStack: " + undoStack.size());
 }
 
 
 public void addShape(XShape xshape) {
  addUndoAction();
  shapeList.add(xshape);

  setChanged();
  notifyObservers();
 }

 // public void pushToUndoStack(){
 ////  undoStack.push(shapeList.toString());
 //  undoStack.push(shapeList.clone());
 // }

 // private ArrayList<XShape> copyShapeList(ArrayList<XShape> shapeList) {
 //  return new ArrayList<XShape>(shapeList);
 // }

 public byte[] serialize(ArrayList<XShape> shapeList) throws IOException {
  ByteArrayOutputStream out = new ByteArrayOutputStream();
  ObjectOutputStream os = new ObjectOutputStream(out);
  os.writeObject(shapeList);
  os.close();
  out.close();
  return out.toByteArray();
 }

 public ArrayList<XShape> deserialize(byte[] data) throws IOException, ClassNotFoundException {
  ByteArrayInputStream in = new ByteArrayInputStream(data);
  ObjectInputStream is = new ObjectInputStream(in);
  is.close();
  in.close();
  return (ArrayList<XShape>) is.readObject();
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
