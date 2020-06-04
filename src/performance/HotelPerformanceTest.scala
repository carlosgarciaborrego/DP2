package dpFinal

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HotelPerformanceTest extends Simulation {

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
					.pause(6)
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

	object ShowHotels {
		val showHotels = exec(http("ShowHotels")
						.get("/petclinic/hotels")
						.headers(headers_0))
						.pause(5)
	}

	object NewHotel {
		val newHotel = 	exec(http("NewHotelForm")
						.get("/petclinic/hotels/new")
						.headers(headers_0)
						.check(css("input[name=_csrf]", "value").saveAs("stoken")))
						.pause(27)
							.exec(http("NewHotelCreated")
							.post("/petclinic/hotels/save")
							.headers(headers_2)
							.formParam("id", "")
							.formParam("name", "Prueba de rendimiento")
							.formParam("location", "Sevilla")
							.formParam("count", "0")
							.formParam("capacity", "10")
							.formParam("petId", "")
							.formParam("_csrf", "${stoken}"))
							.pause(20)
							//.formParam("_csrf", "e148dca1-0d33-4ea2-b3c0-b779f8670de5"))
	}

	object UpdateHotel {
		val updateHotel = exec(http("ShowHotel")
						.get("/petclinic/hotels/6")
						.headers(headers_0)
						.check(css("input[name=_csrf]", "value").saveAs("stoken")))
						.pause(10)
							.exec(http("UpdateHotel")
							.post("/petclinic/hotels/save")
							.headers(headers_2)
							.formParam("id", "6")
							.formParam("name", "Prueba de rendimiento")
							.formParam("location", "Sevilla")
							.formParam("count", "0")
							.formParam("capacity", "5")
							.formParam("_csrf", "${stoken}"))	
							.pause(9)
							//.formParam("_csrf", "e148dca1-0d33-4ea2-b3c0-b779f8670de5"))
	}

	object DeleteHotel {
		val deleteHotel = exec(http("HotelDelete")
						.get("/petclinic/hotels/delete/6")
						.headers(headers_0))
						.pause(5)
	}

	val scn = scenario("HotelPerformanceTest").exec(Home.home,
													Login.login,
													ShowHotels.showHotels,
													NewHotel.newHotel,
													UpdateHotel.updateHotel,
													DeleteHotel.deleteHotel)
		
	setUp(
		scn.inject(rampUsers(2000) during(100 seconds))).protocols(httpProtocol)
														.assertions(
															global.responseTime.max.lt(5000),
															global.responseTime.mean.lt(1000),
															global.successfulRequests.percent.gt(95)
														)
}