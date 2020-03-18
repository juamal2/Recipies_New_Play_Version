package models
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.OFormat
object Note {
  def apply(subject: String, message: String): Note = new Note(BSONObjectID.generate(), subject, message)
}


case class Note (_id: BSONObjectID, subject: String, message :String)

object JsonFormats {
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._
  implicit val Format: OFormat[Note] = Json.format[Note]
}
