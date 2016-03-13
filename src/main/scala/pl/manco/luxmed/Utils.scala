package pl.manco.luxmed

import pl.manco.luxmed.html.DayTerm

object Utils {

  private val NEWLINE = "\n\r"

  def toString(dayTerms: Seq[DayTerm]): String =
    dayTerms.map(dt => (dt.day, NEWLINE + dt.terms.mkString(NEWLINE))).mkString(NEWLINE)
}
