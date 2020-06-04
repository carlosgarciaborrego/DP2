package dpFinal

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ProviderPerformanceTest extends Simulation {

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

	object ShowProviders {
		val showProviders =	exec(http("Providers")
					.get("/petclinic/providers")
					.headers(headers_0))
					.pause(10)
	}

	object NewProvider {
		val newProvider = 	exec(http("NewProvider")
							.get("/petclinic/providers/new")
							.headers(headers_0)
							.check(css("input[name=_csrf]", "value").saveAs("stoken")))
							.pause(53)
								.exec(http("NewProviderCreated")
								.post("/petclinic/providers/save")
								.headers(headers_2)
								.formParam("id", "")
								.formParam("name", "PruebaRendimiento")
								.formParam("city", "Rendimiento")
								.formParam("telephone", "667788964")
								.formParam("description", "Prueba de rendimiento")
								.formParam("clinic", "Petisuit Clinic")
								.formParam("_csrf", "${stoken}"))
								.pause(26)
								//.formParam("_csrf", "e148dca1-0d33-4ea2-b3c0-b779f8670de5"))
	}

	object UpdateProvider {
		val updateProvider = 	exec(http("ShowProvider")
								.get("/petclinic/providers/8")
								.headers(headers_0)
								.check(css("input[name=_csrf]", "value").saveAs("stoken")))
								.pause(34)
									.exec(http("UpdateProvider")
									.post("/petclinic/providers/save")
									.headers(headers_2)
									.formParam("id", "8")
									.formParam("name", "PruebaRendimiento")
									.formParam("city", "Rendimiento")
									.formParam("telephone", "667788964")
									.formParam("description", "Actualizando para Prueba de rendimiento")
									.formParam("clinic", "Petisuit Clinic")
									.formParam("_csrf", "${stoken}"))
									.pause(10)
								//.formParam("_csrf", "e148dca1-0d33-4ea2-b3c0-b779f8670de5"))

	}

	object DeleteProvider {
		val deleteProvider = exec(http("DeleteProvider")
							.get("/petclinic/providers/delete/8")
							.headers(headers_0))
							.pause(4)
	}

	val scn = scenario("ProviderPerformanceTest").exec(	Home.home,
														Login.login,
														ShowProviders.showProviders,
														NewProvider.newProvider,
														UpdateProvider.updateProvider,
														DeleteProvider.deleteProvider)
		

	setUp(
		scn.inject(rampUsers(2000) during(100 seconds))).protocols(httpProtocol)
														.assertions(
															global.responseTime.max.lt(5000),
															global.responseTime.mean.lt(1000),
															global.successfulRequests.percent.gt(95)
														)
}