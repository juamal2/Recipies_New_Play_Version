package models

import play.api.libs.json.OFormat



case class Note (subject: String, message :String)

object JsonFormats {
  import play.api.libs.json.Json
  implicit val feedFormat: OFormat[Note] = Json.format[Note]
}
