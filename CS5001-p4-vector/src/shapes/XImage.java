package shapes;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

// TODO: Auto-generated Javadoc
/**
 * The Class XImage.
 */
public class XImage extends XRect {

 /** The serialized image. */
 private byte[] serializedImage;

 /**
  * Instantiates a new x image.
  *
  * @param bufferedImage the buffered image
  * @param x the x
  * @param y the y
  */
 public XImage(BufferedImage bufferedImage, int x, int y) {
  serializedImage = setImage(bufferedImage);
  shape = new Rectangle2D.Double(x, y, bufferedImage.getWidth(), bufferedImage.getHeight());
 }

 /**
  * Gets the image.
  *
  * @return the image
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
  * Sets the image.
  *
  * @param bufferedImage the buffered image
  * @return the byte[]
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
