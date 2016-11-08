package shapes;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class XImage extends XRect {
 
 private BufferedImage image;
// private Color color = null;
 
 private int x;
 private int y;
 
 
 
 public XImage() {
  
 }
 
 public XImage(BufferedImage image, int x, int y) {
  this.image = image;
  this.x = x;
  this.y = y;
 }

 public BufferedImage getImage() {
  return image;
 }

 public void setImage(BufferedImage image) {
  this.image = image;
 }

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
