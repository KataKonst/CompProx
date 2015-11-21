package client

import java.net.InetSocketAddress
import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.io.IO
import akka.io.Tcp
import akka.io.Tcp.CommandFailed
import akka.io.Tcp.Connect
import akka.io.Tcp.Connected
import akka.io.Tcp.Register
import akka.util.ByteString
import akka.actor.IO.Read
import akka.io.Tcp.Received
import akka.actor.ActorRef
import util.ParseHeader
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import akka.actor.Terminated
import akka.io.Tcp.ConnectionClosed
import http.MimeTypes
import http.HttpHeader
import http.HttpResponseHeaders
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.ByteArrayOutputStream
import com.luciad.imageio.webp.WebPWriteParam
import akka.actor.IO.Close
import akka.io.Tcp.Write
import akka.actor.ActorLogging
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy
import scala.concurrent.duration._
import http.HttpRequestHeaders
import http.HttpHeader
import http.HttpHeader
import ImageProcessing.CompressImage

/**
 * @author katakonst
 */
object Client {
  def props(addres: InetSocketAddress, a: ByteString, sender: ActorRef): Props = {
    Props(new Client(addres, a, sender));

  }

}

class Client(addres: InetSocketAddress, a: ByteString, sendser: ActorRef) extends Actor with ActorLogging {
  import Tcp._
  implicit val ac = ActorSystem("Boss");
  var a = 0;
  IO(Tcp) ! Tcp.Connect(addres);
  var imgflag = false;
  var headerQueue = scala.collection.mutable.Queue[ByteString]();
  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy(maxNrOfRetries = 1, withinTimeRange = 1 second) {

      case _: Exception => SupervisorStrategy.Restart;
    }

  def receive = {
    case CommandFailed(_: Connect) => context stop self // failed to connect

    case c @ Connected(remote, local) =>
      val connection = sender

      connection ! Register(self, true)

      connection ! Write(a)

      context become {
        case data: ByteString        =>
        case CommandFailed(w: Write) =>
        case Received(bytes) =>

          //if(headerQueue.size==0)

          headerQueue += bytes;

          val text = headerQueue.front.utf8String.trim()
          val mapp = ParseHeader.test(text);
          val img = mapp.getOrElse("Content-Type", "sad").trim()

          if (MimeTypes.getTypes().contains(img)) {
            if (!(headerQueue.size == 0)) {
              //         /   headerQueue += bytes;
            }

            val rawByte = headerQueue.foldLeft(ByteString.fromString(""))((g, t) => g ++ t);
            val headerMap = ParseHeader.test(headerQueue.front.utf8String);
            val sz = headerMap.getOrElse("Content-Length", "0").trim()
            val size = Integer.parseInt(sz);

            if (rawByte.length == size +
              (rawByte.utf8String.
                take(rawByte.utf8String.indexOf("\r\n\r\n")).toArray.length + 4)) {

              val contentText = rawByte.utf8String

              var a = java.lang.System.currentTimeMillis();
              val output = CompressImage.Compress((rawByte.takeRight(size))).toArray;
              //                                                println(rawByte.takeRight(size).utf8String)

              println(java.lang.System.currentTimeMillis() - a + "time")


              val content = ByteString.fromArray(output);
              val header = new HttpHeader(ParseHeader.test(contentText).
                +((HttpResponseHeaders.CONTENT_TYPE, MimeTypes.webp)).
                +((HttpResponseHeaders.CONTENT_LENGTH, content.size.toString())), content, contentText.takeWhile { x => x != '\n' })
              sendser ! Tcp.Write(header.getByteString())
              MimeTypes.count = MimeTypes.count + (header.getByteString().length)
              //  sender ! Tcp.Close

            }
          } else {
            sendser ! Tcp.Write(bytes)
            MimeTypes.count = MimeTypes.count + bytes.length;

          }
          println("count=" + MimeTypes.count)

        case "close" => connection ! Tcp.Close

      }
    case PeerClosed => sender ! Tcp.Close

  }

}