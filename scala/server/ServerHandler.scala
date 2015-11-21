package server

import java.net.InetSocketAddress
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.io.Tcp
import akka.actor.Terminated
import util.ParseHeader
import http.HttpUtils
import akka.util.ByteString
import client.Client
import akka.actor.SupervisorStrategy
import akka.actor.SupervisorStrategy.Directive
import akka.actor.OneForOneStrategy
import scala.concurrent.duration._
import client.Boss
import akka.actor.IO.Close
import http.HttpResponseHeaders
import http.HttpRequestHeaders
import http.HttpHeader
/**
 * @author katakonst
 */
object ServerHandler {
  def props(remote: InetSocketAddress, connection: ActorRef, hash:Map[String,String], alias:String, nr:Int): Props =
    Props(new ServerHandler(remote, connection, hash, alias, nr))
}

class ServerHandler(remote: InetSocketAddress, connection: ActorRef,hash:Map[String,String],alias:String,nr:Int) extends Actor {

  implicit val ac= ActorSystem();


//context.watch(connection)
var a=true;
var c=self;
var hashactor=scala.collection.mutable.HashMap[String,ActorRef]();
 var acs=self;
 var w=0;

  def receive: Receive = {
    case Tcp.Received(data) =>
    val text = data.utf8String
    val mapp= ParseHeader.getHeaderParams(text);
       val host=mapp.getOrElse("Host", "").toString();
              val pr=mapp.getOrElse(HttpResponseHeaders.ProxyConnection, "alupigus").toString();
      w+=1;
  println(w+" "+text);
     // val t=hashactor.contains(host);
   //val head=ParseHeader.test(data.utf8String)
       
    // val o=new HttpHeader(head,data,text.takeWhile { x => x != '\n' }).getRequest()
   //val bt=ByteString.fromString(text.replace(HttpResponseHeaders.ProxyConnection, HttpRequestHeaders.CONNECTION));
    context.actorOf(Client.props(new InetSocketAddress(host.trim(),80),data,sender))
    
        
   

     
    case Terminated(connection) =>
   //   println("ASdasdasd");
      connection ! Close;
      context.stop(self)
    case ByteString =>
      println("alupigus")
         

    case _: Tcp.ConnectionClosed =>
            println("closed")
           // context.children.map { x => x ! "close" }
            //acs ! "close";

                    // println(sender+" "+connection)

 //context.stop(connection)
     context.stop(self)
     
     context.stop(acs)
     
  }
}
