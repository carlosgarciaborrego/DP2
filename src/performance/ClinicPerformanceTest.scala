
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ClinicPerformanceTest extends Simulation {

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

	object Clinics {
		val clinics = exec(http("clinics")
			.get("/petclinic/clinic")
			.headers(headers_0))
		.pause(9)
	}
	
	
	object ClinicUpdate {
		val clinicUpdate = exec(http("clinicUpdate")
			.get("/petclinic/clinic/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(1)
		.exec(http("request_7")
			.post("/petclinic/clinic/1")
			.headers(headers_3)
			.formParam("id", "1")
			.formParam("name", "Holly Clinic")
			.formParam("location", "Sevilla")
			.formParam("telephone", "665544331")
			.formParam("emergency", "955910019")
			.formParam("capacity", "50")
			.formParam("email", "clinic1@gmail.com")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(17)
	}
	
		
	object ClinicNew {
		val clinicNew = exec(http("clinicNew")
			.get("/petclinic/clinic/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(1)
		.exec(http("request_9")
			.post("/petclinic/clinic/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "La parroquia")
			.formParam("location", "Malaga")
			.formParam("telephone", "666666666")
			.formParam("emergency", "999999999")
			.formParam("capacity", "23")
			.formParam("email", "laparroquia@gmail.com")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(13)
	}

	object ClinicDelete {
		val clinicDelete = exec(http("clinicDelete")
			.get("/petclinic/clinic/8/delete")
			.headers(headers_0))
		.pause(9)
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

	val scn = scenario("ClinicSimulation").exec(Home.home, 
												Loggin.loggin, 
												Clinics.clinics,
												ClinicUpdate.clinicUpdate, 
												ClinicNew.clinicNew,
												ClinicDelete.clinicDelete,
												LogOut.logOut)



	
	setUp(scn.inject(rampUsers(2000) during (100 seconds))).protocols(httpProtocol)
			.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95))
}