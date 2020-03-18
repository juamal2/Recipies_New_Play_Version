package controllers

import javax.inject._
import play.api.mvc._
import models.Note
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {



  def index(text: String) = Action {
    val notes: List[Note] = List(Note("Task one", "message hello my name is juamal this is ment to be a long message stress test"),Note("Task one", "another one")
      ,Note("Task one", "another one"),Note("Task one", "another one"),Note("Task one", "another one"),Note("Task one", "another one"),Note("Task one", "another one"))


    Ok(views.html.index(s"$text", notes))
  }

}
