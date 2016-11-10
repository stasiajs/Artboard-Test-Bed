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

/**
 * The Class Model describes the model of the application. The model holds an ArrayList with the Shapes that can be drawn. Also the model .
 */
public class Model extends Observable {

 /** The shape list holds the different XShapes that will be drawn by the GUI. */
 private ArrayList<XShape> shapeList;

 /** The redo stack has all the redoable states of the shapeList in a serialized form. */
 private Stack<byte[]> redoStack;

 /** The undo stack has all the undoable states of the shapeList in a serialized form. */
 private Stack<byte[]> undoStack;

 /**
  * Instantiates a new model with a new list of XShapes, an undo stack and a redo stack.
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
    
    setChanged();
    notifyObservers();
   }
   catch (ClassNotFoundException e) {
   }
   catch (IOException e) {
   }

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
    setChanged();
    notifyObservers();
   }
   catch (ClassNotFoundException e) {
   }
   catch (IOException e) {
   }
  }
 }

 /**
  * Adds the undo action.
  */
 public void addUndoAction() {
  try {
   // push current state to the undo stack
   redoStack.clear();
   undoStack.push(serialize(shapeList));
  }
  catch (IOException e) {
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

 /**
  * Serialize.
  *
  * @param shapeList the shape list
  * @return the byte[]
  * @throws IOException Signals that an I/O exception has occurred.
  */
 private byte[] serialize(ArrayList<XShape> shapeList) throws IOException {
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
 private ArrayList<XShape> deserialize(byte[] data) throws IOException, ClassNotFoundException {
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

 public void clearUndoRedo() {
  undoStack.clear();
  redoStack.clear();
 }

}
