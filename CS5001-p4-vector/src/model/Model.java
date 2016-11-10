package model;

import java.awt.Color;
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
  * Undo action for the shape list. If the undo stack has instances of the shape list, the shape list is reset to the top instance of the undo stack.
  * Before the shape list is undone, it gets pushed on the redo stack, so it is redoable. After the change, the observers are notified.
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
  * Redo action for the shape list. If the redo stack has instances of the shape list, the current shape list is set to the top element of the redo stack.
  * As redo actions are undoable as well, the current state of the shape list is updated, before it is set to the top redo element.
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
  * Adds a new undo action. If there were any redoable actions in the redo stack, they are deleted,
  * so actions that were done before the newest action cannot be redone as this is not reasonable.
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
  * Gets the last XShape in the list which contains the position x,y .
  *
  * @param x the x position
  * @param y the y position
  * @return the x shape at position x,y; null if no shape at this position
  */
 public XShape getXShapeAtPos(int x, int y) {
  XShape selectedXShape = null;
  for (int i = 0; i < shapeList.size(); i++) {
   if (shapeList.get(i).isClicked(x, y)) {
    selectedXShape = shapeList.get(i);
   }
  }
  return selectedXShape;
 }

 /**
  * Adds a new XShape to the shape list and notifies the observers. Before adding, an undo action is invoked.
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
  * Removes a specified shape and notfies the observers. Before removing, an undo action is invoked.
  *
  * @param xshape the xshape
  */
 public void removeShape(XShape xshape) {
  addUndoAction();
  shapeList.remove(xshape);
  setChanged();
  notifyObservers();
 }

 /**
  * Clears the shape list and notifies the observers. Before clearing, an undo action is invoked.
  */
 public void clearShapeList() {
  addUndoAction();
  shapeList.clear();
  setChanged();
  notifyObservers();
 }

 /**
  * Sets the color of a specified XShape in the list and notifies the observers. Before changing the color, an undo action is invoked.
  *
  * @param xshape the xshape to change the color
  * @param color the new color
  */
 public void setColor(XShape xshape, Color color) {
  if (shapeList.contains(xshape)) {
   addUndoAction();
   xshape.setColor(color);
   setChanged();
   notifyObservers();
  }

 }

 /**
  * Specifies if the XShape in the list is filled or not and notifies the observers. Before changing the color, an undo action is invoked.
  *
  * @param xshape the XShape to change the fill
  * @param fill the fill is true or false
  */
 public void setFill(XShape xshape, boolean fill) {
  if (shapeList.contains(xshape)) {
   addUndoAction();
   xshape.setFill(fill);
   setChanged();
   notifyObservers();
  }
 }

 /**
  * Serializes the shape list. This needs to be done before it is pushed on one of the stacks.
  *
  * @param shapeList the shape list
  * @return a serialized byte array of the shape list
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
  * Deserializes the shape list. This is done, when another instance of the shape list is fetched from one of the stacks.
  *
  * @param serializedShapeList the serialized shape list
  * @return an array list of shapes
  * @throws IOException Signals that an I/O exception has occurred.
  * @throws ClassNotFoundException the class not found exception
  */
 private ArrayList<XShape> deserialize(byte[] serializedShapeList) throws IOException, ClassNotFoundException {
  ByteArrayInputStream in = new ByteArrayInputStream(serializedShapeList);
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
  * Save the shape list to file.
  *
  * @param filename the filename of the file that shall be saved
  * @throws IOException Signals that an I/O exception has occurred.
  */
 public void saveToFile(String filename) throws IOException {
  FileOutputStream fos = new FileOutputStream(filename);
  ObjectOutputStream oos = new ObjectOutputStream(fos);
  oos.writeObject(shapeList);
  oos.close();
  fos.close();
 }

 /**
  * Read shape list from file.
  *
  * @param filename the filename of the file that shall be read
  * @throws IOException Signals that an I/O exception has occurred.
  * @throws ClassNotFoundException the class not found exception
  */
 public void readFromFile(String filename) throws IOException, ClassNotFoundException {
  FileInputStream fis = new FileInputStream(filename);
  ObjectInputStream ois = new ObjectInputStream(fis);
  ArrayList<XShape> readList = (ArrayList<XShape>) ois.readObject();
  shapeList = readList;
  ois.close();
  fis.close();
 }

 /**
  * Clears the undo and the redo stack.
  */
 public void clearUndoRedo() {
  undoStack.clear();
  redoStack.clear();
 }

}
