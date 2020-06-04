package dpFinal

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ReservationAdminPerformanceTest extends Simulation {

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
					.pause(10)
						.exec(http("Logged")
						.post("/petclinic/login")
						.headers(headers_2)
						.formParam("username", "admin1")
						.formParam("password", "4dm1n")
						.formParam("_csrf", "${stoken}"))
						.pause(9)
						//.formParam("_csrf", "bdfc6ce9-d47e-40ea-ad59-a66e7ce11bc4"))
	}

	object ShowReservations {
		val showReservations = 	exec(http("ShowReservations")
								.get("/petclinic/reservations")
								.headers(headers_0))
								.pause(66)
	}

	object AcceptedReservation {
		val acceptedReservation = exec(http("AcceptedReservation")
								.get("/petclinic/reservations/accepted/1")
								.headers(headers_0))
								.pause(1)
	}

	object RejectedReservation {
		val rejectedReservation = 	exec(http("RejectedReservation")
									.get("/petclinic/reservations/rejected/2")
									.headers(headers_0))
									.pause(19)
	}

	val scn = scenario("ReservationAdminPerformanceTest").exec(	Home.home,
																Login.login,
																ShowReservations.showReservations,
																AcceptedReservation.acceptedReservation,
																RejectedReservation.rejectedReservation)
		
	setUp(
		scn.inject(rampUsers(2000) during(100 seconds))).protocols(httpProtocol)
														.assertions(
															global.responseTime.max.lt(5000),
															global.responseTime.mean.lt(1000),
															global.successfulRequests.percent.gt(95)
														)
}