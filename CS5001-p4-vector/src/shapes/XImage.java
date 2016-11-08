package shapes;

import java.awt.image.BufferedImage;

public class XImage extends XRect {

 private int[] imageArray;

 private int x;
 private int y;
 private int w;
 private int h;
 private int imageType;

// public XImage() {
//  super();
// }

 public XImage(BufferedImage image, int x, int y) {
  
  
  
  
  imageArray = new int[image.getHeight()*image.getWidth()];
  System.out.println("new imageArray constructed");
  image.getRGB(0, 0, image.getWidth()-1, image.getHeight()-1, this.imageArray, 0, 1);
  this.x = x;
  this.y = y;
  this.w = image.getWidth();
  this.h = image.getHeight();
  this.imageType = image.getType();
 }

 public BufferedImage getImage() {
  BufferedImage bufferedImage = new BufferedImage(w, h, imageType);
  bufferedImage.setRGB(0, 0, w-1, h-1, this.imageArray, 0, 1);
  return bufferedImage;
 }

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
