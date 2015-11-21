package http

import akka.util.ByteString

/**
 * @author katakonst
 */
class HttpHeader(headermap: Map[String, String], content: ByteString, response: String) {

  def getByteString(): ByteString = {
    val a = response + (headermap - ("", headermap.get("").get)).
      foldLeft("")((r, c) => r + (c._1 + ":" + c._2 + "\n")) + "\r\n";

    ByteString.fromString(a) ++ content;

  }
  def getRequest():ByteString={
  
    val a = response + (headermap - ("", headermap.get("").get)).
      foldLeft("")((r, c) => r + (c._1 + ":" + c._2 + "\n")).trim();

    ByteString.fromString(a)

  }
}