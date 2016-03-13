package pl.manco.luxmed.html

import org.jsoup.nodes.{Document, Element}

import scala.collection.JavaConversions._

case class DayTerm(day: String, terms: Seq[Term])
case class Term(when: String, who: String, where: String)

object Selector {

  def verificationToken(html: Document): Option[String] =
    html.select("input[name$=__RequestVerificationToken]").`val` match {
      case "" => None
      case token => Some(token)
    }

  def dayTerms(html: Document): Seq[DayTerm] =
    html
      .select("ul.tableList")
      .select("li")
      .map(li => DayTerm(li.select("div.title").text, terms(li)))
      .toSeq

  def terms(html: Element): Seq[Term] =
    html
      .select("table.reserveTable")
      .select("tbody")
      .select("tr")
      .map(
        tr => Term(
          tr.child(0).attr("data-sort"),
          tr.child(1).text(),
          tr.child(3).text()
        )
      ).toSeq
}
