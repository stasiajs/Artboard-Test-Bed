package shapes;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class XImage extends XRect {

 private byte[] serImage;
// private transient BufferedImage bufferedImage;

 private int x;
 private int y;
 private int w;
 private int h;
 

 // public XImage() {
 //  super();
 // }

 public XImage(BufferedImage bufferedImage, int x, int y) {
  this.serImage = setImage(bufferedImage);
  System.out.println(serImage.length);
  this.x = x;
  this.y = y;
  this.h = bufferedImage.getHeight();
  this.w = bufferedImage.getWidth();
  
  shape = new Rectangle2D.Double(x, y, w, h);
 }

 public BufferedImage getImage() {
  try {
   ByteArrayInputStream in = new ByteArrayInputStream(this.serImage);
   ObjectInputStream is = new ObjectInputStream(in);
   is.close();
   in.close();
//   return (BufferedImage) is.readObject();
   
   BufferedImage bufferedImage = ImageIO.read(in);
   System.out.println("returning buffimg");
   return bufferedImage;
  }
  catch (Exception e) {
   e.printStackTrace();
   return null;
  }
 }

 public byte[] setImage(BufferedImage bufferedImage) {
  try {
   ByteArrayOutputStream out = new ByteArrayOutputStream();
   ObjectOutputStream os = new ObjectOutputStream(out);
//   os.writeObject(bufferedImage);
//   os.defaultWriteObject();
   ImageIO.write(bufferedImage, "png", out);
   os.close();
   out.close();
   return out.toByteArray();
  }
  catch (Exception e) {
   e.printStackTrace();
   return null;
  }
 }
 
 @Override
 public void dragTo(int x, int y) {
  super.dragTo(x, y);
  this.x = x-w/2;
  this.y = y-w/2;
//  ((Rectangle2D.Double) shape).setRect(x, y, w, h);
 }
 
 @Override
 public void resize(int x, int y, int corner) {
  
 }

 // public BufferedImage deserialize(byte[] data) throws IOException, ClassNotFoundException {
 //  ByteArrayInputStream in = new ByteArrayInputStream(data);
 //  ObjectInputStream is = new ObjectInputStream(in);
 //  is.close();
 //  in.close();
 //  return (BufferedImage) is.readObject();
 // }

 // public void setImage(BufferedImage imageArray) {
 //  this.image = (SerializableImage) imageArray;
 // }

 public int getX() {
  return x;
 }

 public void setX(int x) {
  this.x = x;
 }

 public int getY() {
  return y;
 }

 public void setY(int y) {
  this.y = y;
 }

}
