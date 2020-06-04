package dpFinal

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class SpecialtyPerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.css""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,ko;q=0.7,zh-CN;q=0.6,zh;q=0.5,bn;q=0.4")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = 	exec(http("Home")
					.get("/petclinic/")
					.headers(headers_0))
					.pause(5)
	}

	object Login {
		val login = exec(http("Login")
					.get("/petclinic/login")
					.headers(headers_0)
					.check(css("input[name=_csrf]", "value").saveAs("stoken")))
					.pause(14)
						.exec(http("Logged")
						.post("/petclinic/login")
						.headers(headers_2)
						.formParam("username", "admin1")
						.formParam("password", "4dm1n")
						.formParam("_csrf", "${stoken}"))
						.pause(8)
						//.formParam("_csrf", "b2e3a73c-67ca-4bcd-82f5-8f89bff44fd9"))
	}

	object ShowVeterinarians {
		val showVeterinarians = 	exec(http("ShowVeterinarians")
									.get("/petclinic/vets")
									.headers(headers_0))
									.pause(11)
	}

	object NewSpecialty {
		val newSpecialty = 	exec(http("NewSpecialtyForm")
							.get("/petclinic/vet/1/specialty/new")
							.headers(headers_0)
							.check(css("input[name=_csrf]", "value").saveAs("stoken")))
							.pause(22)
								.exec(http("NewSpecialtyAdded")
								.post("/petclinic/vet/1/specialty/new")
								.headers(headers_2)
								.formParam("name", "Ophthalmology")
								.formParam("_csrf", "${stoken}"))
								.pause(49)
								//.formParam("_csrf", "e148dca1-0d33-4ea2-b3c0-b779f8670de5"))
	}

	object DeleteSpecialty {
		val deleteSpecialty = 	exec(http("DeleteSpecialty")
								.get("/petclinic/vet/1/specialty/delete/4")
								.headers(headers_0))
								.pause(8)
	}

	val scn = scenario("SpecialtyPerformanceTest").exec(Home.home,
														Login.login,
														ShowVeterinarians.showVeterinarians,
														NewSpecialty.newSpecialty,
														DeleteSpecialty.deleteSpecialty)

	setUp(
		scn.inject(rampUsers(2000) during(100 seconds))).protocols(httpProtocol)
														.assertions(
															global.responseTime.max.lt(5000),
															global.responseTime.mean.lt(1000),
															global.successfulRequests.percent.gt(95)
														)
}