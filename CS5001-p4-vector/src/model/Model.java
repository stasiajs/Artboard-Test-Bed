package model;

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

// TODO: Auto-generated Javadoc
/**
 * The Class Model.
 */
public class Model extends Observable {

 /** The shape list. */
 private ArrayList<XShape> shapeList;

 /** The redo stack. */
 private Stack<byte[]> redoStack;

 /** The undo stack. */
 private Stack<byte[]> undoStack;

 /**
  * Instantiates a new model.
  */
 public Model() {
  shapeList = new ArrayList<XShape>();
  redoStack = new Stack<byte[]>();
  undoStack = new Stack<byte[]>();

 }

 /**
  * Undo.
  */
 public void undo() {
  if (undoStack.size() > 0) {
   try {
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

 /**
  * Redo.
  */
 public void redo() {
  if (redoStack.size() > 0) {
   try {
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

 /**
  * Adds the undo action.
  */
 public void addUndoAction() {

  redoStack.clear();

  try {
   // push current state
   undoStack.push(serialize(shapeList));
  }
  catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }

 /**
  * Adds the shape.
  *
  * @param xshape the xshape
  */
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

 /**
  * Serialize.
  *
  * @param shapeList the shape list
  * @return the byte[]
  * @throws IOException Signals that an I/O exception has occurred.
  */
 public byte[] serialize(ArrayList<XShape> shapeList) throws IOException {
  ByteArrayOutputStream out = new ByteArrayOutputStream();
  ObjectOutputStream os = new ObjectOutputStream(out);
  os.writeObject(shapeList);
  os.close();
  out.close();
  return out.toByteArray();
 }

 /**
  * Deserialize.
  *
  * @param data the data
  * @return the array list
  * @throws IOException Signals that an I/O exception has occurred.
  * @throws ClassNotFoundException the class not found exception
  */
 public ArrayList<XShape> deserialize(byte[] data) throws IOException, ClassNotFoundException {
  ByteArrayInputStream in = new ByteArrayInputStream(data);
  ObjectInputStream is = new ObjectInputStream(in);
  is.close();
  in.close();
  return (ArrayList<XShape>) is.readObject();
 }

 /**
  * Gets the shape list.
  *
  * @return the shape list
  */
 public ArrayList<XShape> getShapeList() {
  return shapeList;
 }

 /**
  * Save to file.
  *
  * @param filename the filename
  * @throws IOException 
  */
 public void saveToFile(String filename) throws IOException {
  FileOutputStream fos = new FileOutputStream(filename);
  ObjectOutputStream oos = new ObjectOutputStream(fos);
  oos.writeObject(shapeList);
  oos.close();
  fos.close();
 }

 /**
  * Read from file.
  *
  * @param filename the filename
  */
 public void readFromFile(String filename) throws IOException, ClassNotFoundException {
  FileInputStream fis = new FileInputStream(filename);
  ObjectInputStream ois = new ObjectInputStream(fis);
  ArrayList<XShape> readList = (ArrayList<XShape>) ois.readObject();
  shapeList = readList;
  ois.close();
  fis.close();
 }

 public void clearUndoRedo(){
  undoStack.clear();
  redoStack.clear();
 }
 
}
