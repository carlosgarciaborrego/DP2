
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class VetPerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
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
	
	object ListVets {
		val listVets = exec(http("vets")
			.get("/petclinic/vets")
			.headers(headers_0))
		.pause(16)	
	}

	object EditVetSucces {
		val editVetSucces = exec(http("show_one_vet")
			.get("/petclinic/vet/show/1")
			.headers(headers_0)
			 .check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(14)
		.exec(http("edit_one_vet")
			.post("/petclinic/vet/show/1")
			.headers(headers_3)
			.formParam("id", "1")
			.formParam("firstName", "Juan")
			.formParam("lastName", "Carter")
			.formParam("address", "110 W. Liberty St.")
			.formParam("city", "Madison")
			.formParam("telephone", "6085551023")
			.formParam("user.username", "vet1")
			.formParam("user.password", "v3t")
			.formParam("clinic", "Holly Clinic")
			.formParam("_csrf", "${stoken}"))
		.pause(27)
	}
	
	object EditVetError {
			val editVetError = exec(http("show_one_vet")
			.get("/petclinic/vet/show/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(14)
		.exec(http("edit_with_error_one_vet_2")
			.post("/petclinic/vet/show/1")
			.headers(headers_3)
			.formParam("id", "1")
			.formParam("firstName", "")
			.formParam("lastName", "Carter")
			.formParam("address", "110 W. Liberty St.")
			.formParam("city", "Madison")
			.formParam("telephone", "6085551023")
			.formParam("user.username", "vet1")
			.formParam("user.password", "v3t")
			.formParam("clinic", "Holly Clinic")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}
	
	object LogoOut {
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
	
	val editVetSuccesScn = scenario("EditVetSucces").exec(Home.home, 
												Loggin.loggin, 
												ListVets.listVets,
												EditVetSucces.editVetSucces,
												ListVets.listVets,
												LogoOut.logOut)

	val editVetErrorScn = scenario("EditVetError").exec(Home.home, 
												Loggin.loggin, 
												ListVets.listVets,
												EditVetError.editVetError, 
												ListVets.listVets,
												LogoOut.logOut)


	setUp(editVetSuccesScn.inject(rampUsers(2000) during (100 seconds)), 
			editVetErrorScn.inject(rampUsers(2000) during (100 seconds))
			).protocols(httpProtocol)
			.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95))
}