package shapes;

import java.awt.Graphics2D;
import java.awt.Image;
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

 private byte[] serializedImage;

 public XImage(BufferedImage bufferedImage, int x, int y) {
  this.serializedImage = setImage(bufferedImage);
  System.out.println(serializedImage.length);
  this.x1 = x;
  this.y1 = y;
  this.height = bufferedImage.getHeight();
  this.width = bufferedImage.getWidth();

  shape = new Rectangle2D.Double(x, y, width, height);
 }

 public BufferedImage getImage() {
  try {
   ByteArrayInputStream bais = new ByteArrayInputStream(this.serializedImage);
   ObjectInputStream ois = new ObjectInputStream(bais);
   ois.close();
   bais.close();
   BufferedImage bufferedImage = ImageIO.read(bais);
   return bufferedImage;
  }
  catch (Exception e) {
   return null;
  }
 }

 public byte[] setImage(BufferedImage bufferedImage) {
  try {
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   ObjectOutputStream oos = new ObjectOutputStream(baos);
   ImageIO.write(bufferedImage, "png", baos);
   oos.close();
   baos.close();
   return baos.toByteArray();
  }
  catch (Exception e) {
   return null;
  }
 }

}
