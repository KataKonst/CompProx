package http

/**
 * @author katakonst
 */
object MimeTypes {
  val bitmap="image/bmp";
  val Gif="image/gif";
  val JPEG="image/jpeg"
  val png="image/png";
  val webp="image/webp";
  var count=0;
    var count2=0;

  
  
  def getTypes():Map[String,String]={
    Map(MimeTypes.bitmap->"e",
        MimeTypes.Gif->"e",
        MimeTypes.JPEG->"e",
        MimeTypes.png->"e",
        MimeTypes.webp->"e"
        )
  }
}

class MimeTypes {
  
  
}