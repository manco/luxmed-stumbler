package pl.manco.luxmed

import java.time.LocalDate
import java.time.LocalDate._
import java.time.format.DateTimeFormatter._

import com.typesafe.config.Config
import org.apache.http.client.methods.{HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.{BasicCookieStore, HttpClients, LaxRedirectStrategy}
import org.apache.http.util.EntityUtils

/*
  * TODO abstract http request builder out, compare with dispatch or spray-client
  */
class LuxmedSession(config: Config) {

  private val WROCLAW_CODE = "5"
  private val DENTIST_CODE = "6621"
  private val DATE_PATTERN = ofPattern("dd-MM-yyyy")

  private val user = config.getString("luxmed.user")
  private val pwd = config.getString("luxmed.password")
  private val urls = new {
    val login = config.getString("luxmed.urls.login")
    val reservations = config.getString("luxmed.urls.reservations")
  }

  private val cookieStore = new BasicCookieStore
  private val httpClient = HttpClients
    .custom()
    .setDefaultCookieStore(cookieStore)
    .setRedirectStrategy(new LaxRedirectStrategy)
    .build()

  def login() = {
    val request = RequestBuilder
      .post(urls.login)
      .addParameter("Login", user)
      .addParameter("Password", pwd)
      .build

    execute(request)
  }

  def searchDentists(implicit verificationToken: String) = {
    val request = RequestBuilder
      .post(urls.reservations)
      .addParameter("IsFromStartPage", true.toString)
      .addParameter("SearchFirstFree", true.toString)
      .addParameter("__RequestVerificationToken", verificationToken)
      .addParameter("TimeOption", "Any")
      .addParameter("CityId", WROCLAW_CODE)
      .addParameter("ServiceId", DENTIST_CODE)
      .addParameter("DateFrom", format(now))
      .build

    execute(request)
  }

  private def execute(request: HttpUriRequest) =
    EntityUtils.toString(httpClient.execute(request).getEntity)

  private def format(date: LocalDate) =
    date.format(DATE_PATTERN)

  def cookies = cookieStore.getCookies
}
