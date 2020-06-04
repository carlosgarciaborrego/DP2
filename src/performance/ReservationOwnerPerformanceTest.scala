package dpFinal

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ReservationOwnerPerformanceTest extends Simulation {

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
					.pause(8)
	}

	object Login {
		val login = exec(http("Login")
					.get("/petclinic/login")
					.headers(headers_0)
					.check(css("input[name=_csrf]", "value").saveAs("stoken")))
					.pause(12)
						.exec(http("Logged")
						.post("/petclinic/login")
						.headers(headers_2)
						.formParam("username", "owner1")
						.formParam("password", "0wn3r")
						.formParam("_csrf", "${stoken}"))
						.pause(49)
						//.formParam("_csrf", "3b61e06e-f84b-4213-b70b-d94ecef3cd7e"))
	}

	object ShowReservations {
		val showReservations = 	exec(http("ShowReservations")
								.get("/petclinic/reservations")
								.headers(headers_0))
								.pause(11)									
	}

	object NewReservation {
		val newReservation = exec(http("NewReservationForm")
							.get("/petclinic/reservations/new")
							.headers(headers_0)
							.check(css("input[name=_csrf]", "value").saveAs("stoken")))
							.pause(35)
								.exec(http("ReservationCreated")
								.post("/petclinic/reservations/save")
								.headers(headers_2)
								.formParam("id", "")
								.formParam("owner.id", "")
								.formParam("status", "pending")
								.formParam("telephone", "12312312")
								.formParam("reservationDate", "2020/09/01")
								.formParam("responseClient", "Esto es para la prueba de rendimiento")
								.formParam("clinic", "Petisuit Clinic")
								.formParam("_csrf", "${stoken}"))
								.pause(12)
								//.formParam("_csrf", "9d84511f-d82d-41ac-ba73-2f4c61f7b293"))
	}

	object UpdateReservation {
		val updateReservation = 	exec(http("ShowReservation")
								.get("/petclinic/reservations/8")
								.headers(headers_0)
								.check(css("input[name=_csrf]", "value").saveAs("stoken")))
								.pause(26)
									exec(http("UpdateReservation")
									.post("/petclinic/reservations/save")
									.headers(headers_2)
									.formParam("id", "8")
									.formParam("telephone", "12312312")
									.formParam("reservationDate", "2020/09/01")
									.formParam("status", "pending")
									.formParam("clinic", "Petisuit Clinic")
									.formParam("owner.id", "1")
									.formParam("responseClient", "Esto es para la prueba de rendimiento, ahora estoy actualizando")
									.formParam("_csrf", "${stoken}"))
									.pause(56)
									//.formParam("_csrf", "9d84511f-d82d-41ac-ba73-2f4c61f7b293"))
	}

	object DeleteReservation {
		val deleteReservation = exec(http("DeleteReservation")
								.get("/petclinic/reservations/delete/8")
								.headers(headers_0))
								.pause(6)
	}

	val scn = scenario("ReservationOwnerPerformanceTest").exec(Home.home,
																Login.login,
																ShowReservations.showReservations,
																NewReservation.newReservation,
																UpdateReservation.updateReservation,
																DeleteReservation.deleteReservation)

	setUp(
	scn.inject(rampUsers(2000) during(100 seconds))).protocols(httpProtocol)
													.assertions(
														global.responseTime.max.lt(5000),
														global.responseTime.mean.lt(1000),
														global.successfulRequests.percent.gt(95)
													)
}