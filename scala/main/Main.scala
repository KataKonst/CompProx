package main

import java.net.InetSocketAddress
import akka.actor.ActorSystem
import server.Server
import GUi.MainFrame
import util.FilesUtilities
import akka.util.ByteString.ByteString1
import akka.util.ByteString
import ImageProcessing.CompressImage

/**
 * @author katakonst
 */
object Main {
  System.load("/home/katakonst/Downloads/webp-imageio-0.4.2/linux-x64/libwebp-imageio.so");
  //   System.setProperty("java.library.path", "/home/katakonst/Downloads/webp-imageio-0.4.2/linux-x64/libwebp-imageio.so");
  def main(args: Array[String]): Unit =
    {
    //val a=      java.lang.System.currentTimeMillis();

//CompressImage.Compress("/home/katakonst/Downloads/36932.png")
//                  println(      java.lang.System.currentTimeMillis()-a+"time")

      println(System.getProperty("java.library.path"))
      
      new MainFrame().setVisible(true);

    }
}