package shapes;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

/**
 * The Class XImage inherits from XRect and holds an underlying Image besides a Rectangle, that is responsible for the draw operations.
 */
public class XImage extends XRect {

 /** The underling image in a serialized byte array. */
 private byte[] serializedImage;

 /**
  * Instantiates a new XImage.
  *
  * @param bufferedImage the underlying image
  * @param x the x coordinate where the picture is put
  * @param y the y coordinate where the picture is put
  */
 public XImage(BufferedImage bufferedImage, int x, int y) {
  serializedImage = setImage(bufferedImage);
  x1 = x;
  y1 = y;
  height = bufferedImage.getHeight();
  width = bufferedImage.getWidth();
  x2 = x1 + width;
  y2 = y1 + height;

  shape = new Rectangle2D.Double(x, y, width, height);
 }

 /**
  * Deserializes and gets the image from the byteArray, where it is stored in.
  *
  * @return the image as a BufferedImage
  */
 public BufferedImage getImage() {
  try {
   ByteArrayInputStream bais = new ByteArrayInputStream(serializedImage);
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

 /**
  * Serializes and sets the image as a PNG to a byte array.
  *
  * @param bufferedImage the buffered image
  * @return the serialized byte array of the image
  */
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
