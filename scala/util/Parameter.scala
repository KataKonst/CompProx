package util

import akka.util.ByteString

/**
 * @author katakonst
 */
class Parameter(name:String,value:String) {
  
  def getName=name;
  def getValue=value;
 
  
  override
  def toString():String="name="+name+"value="+value+"\n";
  
  
  
}