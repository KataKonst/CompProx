package server

import akka.actor.Props
import java.net.InetSocketAddress
import akka.io.Tcp
import akka.io.IO
import akka.actor.{ Actor, ActorRef, Props }
import akka.actor.ActorSystem
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy
import scala.concurrent.duration._
import akka.actor.Terminated
import http.MimeTypes

/**
 * @author katakonst
 */
object Server {
  def props(addres: InetSocketAddress, hash: Map[String, String], alias: String): Props = {
    Props(new Server(addres, hash, alias));

  }

}

class Server(addres: InetSocketAddress, hash: Map[String, String], alias: String) extends Actor {
  implicit val ac = ActorSystem("Muie");

  IO(Tcp) ! Tcp.Bind(self, addres, 100, List(Tcp.SO.KeepAlive(true)))
  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy(maxNrOfRetries = 1, withinTimeRange = 1 second) {

      case _: Exception => SupervisorStrategy.Restart;
    }
//val p=context.actorOf(ServerHandler.props(null, null, hash, alias, 0))
  def receive: Actor.Receive = {
    case Tcp.Connected(remote, _) =>
      val a = context.children.toList.length;
      sender ! Tcp.Register(context.actorOf(ServerHandler.props(null, sender, hash, alias, 0)));
        MimeTypes.count2 = MimeTypes.count2 + 1;
println( MimeTypes.count2)
    case Terminated(connection) =>
      context.stop(self)
    case _: Tcp.ConnectionClosed =>
      context.stop(self)
    case "close"=>
            println("close")


  }

}
  
