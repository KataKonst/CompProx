package ImageProcessing

import akka.util.ByteString
import javax.imageio.ImageIO
import com.luciad.imageio.webp.WebPWriteParam
import java.io.ByteArrayOutputStream
import javax.imageio.IIOImage
import javax.imageio.stream.ImageOutputStream
import java.awt.image.RenderedImage
import java.io.File

/**
 * @author katakonst
 */
object CompressImage {
  def Compress(image: ByteString): ByteString = {

    val imgWriter = ImageIO.getImageWritersByFormatName("webp").next();
    val imgWriteParams = new WebPWriteParam(null);
    imgWriteParams.setCompressionType("Lossy");
    val output = new ByteArrayOutputStream();
    imgWriteParams.setCompressionQuality(0);

    //imgWriter.setOutput(output);
    //imgWriter.write(null, new IIOImage( ImageIO.read(image.iterator.asInputStream), null, null), imgWriteParams);
    ImageIO.write(ImageIO.read(image.iterator.asInputStream),
      "webp",
      output)
    ByteString.fromArray(output.toByteArray());

  }

  def Compress(file: String): Unit = {

    val imgWriter = ImageIO.getImageWritersByFormatName("webp").next();
    val imgWriteParams = new WebPWriteParam(null);
    //  imgWriteParams.setCompressionType("Lossy");
    val output = new ByteArrayOutputStream();
    imgWriteParams.setCompressionQuality(0);

    //imgWriter.setOutput(output);
    //imgWriter.write(null, new IIOImage( ImageIO.read(image.iterator.asInputStream), null, null), imgWriteParams);
    ImageIO.write(ImageIO.read(new File(file)),
      "webp",
      output)
    ByteString.fromArray(output.toByteArray());

  }
}

class CompressImage {

}