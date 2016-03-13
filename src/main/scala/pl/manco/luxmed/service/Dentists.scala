package pl.manco.luxmed.service

import com.typesafe.config.Config
import org.jsoup.Jsoup.parse
import pl.manco.luxmed.html.Selector
import pl.manco.luxmed.{LuxmedSession, Utils}

/*
  1. spring scheduled czasowe sprawdzanie + guava eventBus
  2. powiadomienia na gtalk (use smack) / wyslanie maila
  3. external luxmed/gtalk config
  4. some better query (in notifier)
  5. query layer
 */
class Dentists(luxmedConfig: Config) {

  def find: String = {
    val luxmedSession = new LuxmedSession(luxmedConfig)

    //TODO inform that service unavailable

    Selector.verificationToken(parse(luxmedSession.login())) map {
      t => Utils.toString(Selector.dayTerms(parse(luxmedSession.searchDentists(t))))
    } getOrElse "Can't extract verification token"
  }
}
