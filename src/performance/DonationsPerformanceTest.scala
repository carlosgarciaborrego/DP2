package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DonationsPerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/petclinic/")
			.headers(headers_0))
		.pause(7)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/petclinic/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(16)
		.exec(http("Logged")
			.post("/petclinic/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(25)
	}
	
	object CausesList {
		val causesList = exec(http("CausesList")
			.get("/petclinic/causes")
			.headers(headers_0))
		.pause(22)
	}

	object DonationForm {
		val donationForm = exec(http("DonationForm")
			.get("/petclinic/causes/donate/6")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(23)
		.exec(http("CausesListAfterDonation")
			.post("/petclinic/causes/donate/")
			.headers(headers_2)
			.formParam("cause.id", "1")
			.formParam("name", "Donacion 1")
			.formParam("amount", "100.0")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}
	

	val DonationScn = scenario("DonationPerformanceTests").exec(Home.home,
														Login.login,
														CausesList.causesList,
														DonationForm.donationForm)

	setUp(
		DonationScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}