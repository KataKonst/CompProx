package http

import akka.util.ByteString
import java.awt.Robot
import java.awt.Rectangle
import java.awt.Toolkit
import java.nio.ByteBuffer
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/**
 * @author katakonst
 */
object HttpUtils {
  def sendFileHeader(data: ByteString): ByteString = {

    val resp = ByteString.fromString(HttpHeaderList.HTTP11 + " " + HttpHeaderList.ResponseOK + "\n");
    resp ++ HttpHeaderParameter(HttpHeaderList.ContentType, HttpHeaderList.octetSTream) ++ HttpHeaderParameter(HttpHeaderList.ContentLength, data.length.toString()) ++ ByteString.fromString("\n") ++ data

  }

  def sendText(data: String): ByteString = {

    val resp = ByteString.fromString(HttpHeaderList.HTTP11 + " " + HttpHeaderList.ResponseOK + "\n");
    resp ++ HttpHeaderParameter(HttpHeaderList.ContentType, HttpHeaderList.texthtml) ++ HttpHeaderParameter(HttpHeaderList.ContentLength, data.length.toString()) ++ ByteString.fromString("\n") ++ ByteString.fromString(data)

  }

  def sendIMage(): ByteString = {
    val image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    val baos = new ByteArrayOutputStream();
    ImageIO.write(image, "jpg", baos);
    baos.flush();
    val resp = ByteString.fromString(HttpHeaderList.HTTP11 + " " + HttpHeaderList.ResponseOK + "\n");
    resp ++ HttpHeaderParameter(HttpHeaderList.ContentType, "image/jpeg") ++ HttpHeaderParameter(HttpHeaderList.ContentLength, baos.size().toString()) ++ ByteString.fromString("\n") ++ ByteString.fromArray(baos.toByteArray())

  }
}