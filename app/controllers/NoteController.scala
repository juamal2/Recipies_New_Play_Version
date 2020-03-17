package controllers

import javax.inject.Inject
import models.JsonFormats._
import models.Note
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}




class NoteController @Inject()(
                        components: ControllerComponents,
                        val reactiveMongoApi: ReactiveMongoApi
                        ) extends AbstractController(components)
                        with MongoController with ReactiveMongoComponents {

  implicit  def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("notes"))



  def createNote: Action[AnyContent] = Action.async {
    val note = Note("juamal", "get notes working")
    val futureResult = collection.flatMap(_.insert.one(note))
    futureResult.map(_ => Ok("Note added"))
  }

  def createNoteFromJason: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Note].map { note =>
      collection.flatMap(_.insert.one(note)).map{_ => Ok("note has been inserted")
      }
    }.getOrElse(Future.successful(BadRequest("invalid Json")))
  }

  def removeNote(subject : String): Action[AnyContent] = Action.async{
    val result  = collection.map{
      _.findAndRemove(Json.obj("subject" -> subject))
    }
    result.map{_ => Ok("Success")}
  }
  def updateNoteFromJason: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Note].map {

    }  }

  def readAllNotes: Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[Note]] = collection.map{
      _.find(Json.obj()).
        cursor[Note]()
    }
    val futureNotesList: Future[List[Note]] =
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[Note]]()
        )
      )
    futureNotesList.map { notes =>
      Ok(notes.toString())
    }

  }


















}
