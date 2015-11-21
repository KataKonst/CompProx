package util

import akka.util.ByteString
import http.HttpUtils

/**
 * @author katakonst
 */
object ParseHeader {

  def getParameters(request: String): List[Parameter] =
    {
      val method = request.take(3);
      def mat(metho: String) = metho match {
        case "GET" => ParseHeader.getParams(request.drop(request.indexOf('?') + 1).takeWhile { x => x != ' ' })
        case _     => List()
      }
      mat(method);

    }
  def getHeaderParams(request: String): Map[String, String] = {
    val ls = request.dropWhile { x => x != '\n' }.split('\n').map { x => x.split(':') }.map(x => if (x.length <= 1) { ("", "") } else (x(0), x(1)))

    val a = ls.toMap
    a

  }
  def test(request: String): Map[String, String] =
    {
      val content = request.indexOf("\r\n\r\n");
      val ls = request.dropWhile { x => x != '\n' }.take(content).split('\n').map { x =>
        val a = x.indexOf(':');
        (x.take(a), x.drop(a + 1))
      }

      ls.toMap

    }
  def getParams(request: String): List[Parameter] =
    {
      println(request)
      val ls = request.split('&').map { x => x.split('=') }
      ls.map { x => new Parameter(x.apply(0), x.apply(1)) }.toList
    }

  def getResponse(request: String, hash: Map[String, String], alias: String): ByteString = {


    def mat(pam: Parameter) = pam.getName match {
      case "file" => HttpUtils.sendFileHeader(ByteString.fromArray(FilesUtilities.readFile(hash.getOrElse(pam.getValue, "kjg"))))
      case "path" => HttpUtils.sendText(hash.getOrElse(pam.getValue, "kjg").drop(alias.length()))
      case _      => HttpUtils.sendText("parameter not found")
    }
    HttpUtils.sendIMage();
  }
}

class ParseHeader {

}