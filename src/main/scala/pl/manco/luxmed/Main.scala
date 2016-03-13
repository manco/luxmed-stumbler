package pl.manco.luxmed

import java.io.File

import com.typesafe.scalalogging.LazyLogging
import pl.manco.luxmed.config.ConfigLoader
import pl.manco.luxmed.service.Dentists

object Main extends App with LazyLogging {

  private val credentialsFile = new File("./luxmed-credentials.conf")
  val cfg = ConfigLoader.load(args, Some(credentialsFile))
  if (cfg.isEmpty) println("no user name or password provided at all - nothing will happen")
  cfg.foreach { c => println(new Dentists(c).find) }
}