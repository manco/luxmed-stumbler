package pl.manco.luxmed.html

import org.jsoup.Jsoup.parse
import org.junit.{Assert, Test}

import scala.io.Source

class SelectorTest {
  val resultHtml = Source.fromFile("src/test/resources/luxmed-result.html")("UTF-8").mkString

  @Test
  def shouldSelectDayTerms() = {
    //when
    val result = Selector.dayTerms(parse(resultHtml))

    //then
    Assert.assertEquals(result.size, 4)
  }

  @Test
  def shouldSelectTerms() = {

    //when
    val result = Selector.terms(parse(resultHtml))

    //then
    Assert.assertEquals(result.size, 57)
  }

  @Test
  def shouldExtractVerificationToken() = {
    //given
    val afterLoginSuccess = "<p>something something</p><input name=\"__RequestVerificationToken\" value=\"secretToken\"/>something more"

    //when
    val result = Selector.verificationToken(parse(afterLoginSuccess))

    //then
    Assert.assertEquals(result.get, "secretToken")
  }
}
