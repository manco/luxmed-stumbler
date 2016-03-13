package pl.manco.luxmed.config

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConversions._


object ConfigLoader extends LazyLogging {
  private val parser = new scopt.OptionParser[Params]("luxmed") {
    opt[String]('u', "user") action { (x, c) => c.copy(user = x) } text "Luxmed user (email)"
    opt[String]('p', "password") action { (x, c) => c.copy(pwd = x) } text "Luxmed password"
  }

  def load(args: Array[String], credentialsFile: Option[File] = None): Option[Config] = {
    val cliParams = parser.parse(args, NoParams)
      .filter(params => params.user != null && params.pwd != null)
      .map(params => Map("luxmed.user" -> params.user, "luxmed.password" -> params.pwd))
      .getOrElse(Map())

    val cliConfig = ConfigFactory.parseMap(cliParams)

    val fileConfig = credentialsFile map ConfigFactory.parseFile getOrElse ConfigFactory.empty

    val cfg = ConfigFactory.load
      .withFallback(cliConfig)
      .withFallback(fileConfig)
      .resolve

    Option(cfg)
      .filter(_.hasPath("luxmed.user"))
      .filter(_.hasPath("luxmed.password"))
  }
}

private case class Params(user: String, pwd: String)
private object NoParams extends Params(null, null)
