
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PetHistoryPerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


	object Home {
		val home = exec(http("home")
			.get("/petclinic/")
			.headers(headers_0))
		.pause(9)
	}

	object Loggin {
		val loggin = exec(http("login_1")
			.get("/petclinic/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(38)
		.exec(http("login_2")
			.post("/petclinic/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}
	object Profile {
		val profile = exec(http("profile")
			.get("/petclinic/vet/profile")
			.headers(headers_0))
		.pause(16)	
	}
	
	object PetHistory1 {
		val petHistory1 = exec(http("petHistory1")
			.get("/petclinic/vet/1/pets/1/pethistory")
			.headers(headers_0))
		.pause(16)	
	}

	object PetHistoryShow1 {
		val petHistoryShow1 = exec(http("petHistoryShow1")
			.get("/petclinic/vet/1/pets/1/pethistory/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		
		.pause(13)	
		.exec(http("petHistoryEdit1")
			.post("/petclinic/vet/1/pets/1/pethistory/1")
			.headers(headers_3)
			.formParam("id", "1")
			.formParam("date", "2010/09/07")
			.formParam("summary", "summary")
			.formParam("details", "details 2")
			.formParam("_csrf", "${stoken}"))
		.pause(23)
	}
	
	object PetHistoryDelete{
		val petHistoryDelete1 = exec(http("petHistoryDelete1")
			.get("/petclinic/vet/1/pets/1/pethistory")
			.headers(headers_0))
		.pause(10)	
	}

	object PetHistoryNew{
		val petHistoryNew = exec(http("petHistoryNew")
			.get("/petclinic/vet/1/pets/1/pethistory/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(14)
		// new
		.exec(http("request_10")
			.post("/petclinic/vet/1/pets/1/pethistory/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("date", "")
			.formParam("summary", "resumen")
			.formParam("details", "detalles")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	object LogOut {
		val logOut = exec(http("logout_1")
			.get("/petclinic/logout")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("logout_2")
			.post("/petclinic/logout")
			.headers(headers_3)
			.formParam("_csrf", "${stoken}"))
	}

	

	val scn = scenario("PetHistorySucces").exec(Home.home, 
												Loggin.loggin, 
												Profile.profile,
												PetHistory1.petHistory1, 
												PetHistoryShow1.petHistoryShow1, 
												PetHistoryDelete.petHistoryDelete1,
												PetHistoryNew.petHistoryNew,
												LogOut.logOut)



	
	setUp(scn.inject(rampUsers(2000) during (100 seconds))).protocols(httpProtocol)
			.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95))
}