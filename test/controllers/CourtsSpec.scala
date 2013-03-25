package controller

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._


class CourtsSpec extends Specification {
	import models._
	
		"Courts" should {
			"index page to the court " in {
				val result = controllers.Courts.index(FakeRequest())
				status(result) must equalTo(OK)
  				contentType(result) must beSome("text/html")
  				charset(result) must beSome("utf-8")
  				contentAsString(result) must contain("home")
			}
		}
}