package http

import akka.util.ByteString

/**
 * @author katakonst
 */
object HttpHeaderParameter {
  def apply(name: String, value: String): ByteString =
    {
      ByteString.fromString(name + ": " + value + "\n")
    }
}

class HttpHeaderParameter(name: String, value: String) {
  def getName = name;
  def getValue = value;

  override def toString(): String = name + ": " + value;

  def toByteString(): ByteString = {
    ByteString.fromString(name + ": " + value + "\n")

  }

}