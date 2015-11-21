package util

import java.io.File

/**
 * @author katakonst
 */


object FilesUtilities {
  
  def listFiles(folder:File):Array[File] = folder.isDirectory() match
  {
    case true =>folder.listFiles()
    case _ => Array[File]();
  }
  
  def readFile(file:String):Array[Byte]={
     scala.io.Source.fromFile(file,"utf-8").map { x => x.toByte }.toArray
  }
  def getALiasmap(path:String):Map[String,String]=
  {
     scala.io.Source.fromFile(path).mkString.split("\n").
     map(x=>x.split(':')).
     map (x => (x(1),x(0))).toMap
     
  }
  
}