package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PerformanceTests extends Simulation {

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

	val headers_3 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-CryptoAPI/10.0")

	object Home {
		val home = exec(http("Home")
			.get("/petclinic/")
			.headers(headers_0))
		.pause(7)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/petclinic/login")
			.headers(headers_0))
		.pause(16)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/petclinic/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "be108b97-8683-4f82-ae0c-89dd5178ebad"))

		.pause(25)
	}
	
	object CausesList {
		val causesList = exec(http("CausesList")
			.get("/petclinic/causes")
			.headers(headers_0))
		.pause(22)
	}
	
	object NewCauseForm {
		val newCauseForm = exec(http("NewCauseForm")
			.get("/petclinic/causes/new")
			.headers(headers_0))
		.pause(16)
	}
	
	object CausesListAfterSave {
		val causesListAfterSave = exec(http("CausesListAfterSave")
			.post("/petclinic/causes/save")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("name", "Donacion 1")
			.formParam("description", "Description Cause")
			.formParam("organisation", "Organisation Cause")
			.formParam("budgetTarget", "100.0")
			.formParam("budgetArchivied", "0.0")
			.formParam("causeId", "")
			.formParam("budgetArchivied", "0.0")
			.formParam("_csrf", "b67ac400-ef8f-402c-b23b-4d4d8c2f20e7"))
		.pause(20)
	}
	
	object CauseShow {
		val causeShow = exec(http("CauseShow")
			.get("/petclinic/causes/6")
			.headers(headers_0))
		.pause(21)
	}
	
	object CausesListAfterUpdate {
		val causesListAfterUpdate = exec(http("CausesListAfterUpdate")
			.post("/petclinic/causes/save")
			.headers(headers_2)
			.formParam("id", "6")
			.formParam("name", "Donacion 2")
			.formParam("description", "Description Cause")
			.formParam("organisation", "Organisation Cause")
			.formParam("budgetTarget", "100.0")
			.formParam("budgetArchivied", "0.0")
			.formParam("_csrf", "b67ac400-ef8f-402c-b23b-4d4d8c2f20e7"))
		.pause(14)
	}

	object DonationForm {
		val donationForm = exec(http("DonationForm")
			.get("/petclinic/causes/donate/6")
			.headers(headers_0))
		.pause(23)
	}
	
	object CausesListAfterDonation {
		val causesListAfterDonation = exec(http("CausesListAfterDonation")
			.post("/petclinic/causes/donate/")
			.headers(headers_2)
			.formParam("cause.id", "1")
			.formParam("name", "Donacion 1")
			.formParam("amount", "100.0")
			.formParam("_csrf", "52d577ae-8d72-4734-abe6-7344bb93b834"))
		.pause(16)
	}
	
	object CausesListAfterDelete {
		val causesListAfterDelete = exec(http("CausesListAfterDelete")
			.get("/petclinic/causes/delete/6")
			.headers(headers_0))
		.pause(8)
	}
	
	
	
	val causesScn = scenario("CausesPerformanceTests").exec(Home.home,
													Login.login,
													Logged.logged,
													CausesList.causesList,
													NewCauseForm.newCauseForm,
													CausesListAfterSave.causesListAfterSave,
													CauseShow.causeShow,
													CausesListAfterUpdate.causesListAfterUpdate,
													CausesListAfterDelete.causesListAfterDelete)


	val DonationScn = scenario("DonationPerformanceTests").exec(Home.home,
														Login.login,
														Logged.logged,
														CausesList.causesList,
														DonationForm.donationForm,
														CausesListAfterDonation.causesListAfterDonation)

	setUp(
		causesScn.inject(atOnceUsers(1)),
		DonationScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}