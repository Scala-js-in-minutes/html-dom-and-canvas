package scalajs.hello

import org.scalajs.dom.{Event, html}
import scalatags.JsDom.all._

class AsciiArtTextArea(initialValue: String, onChange: List[String] => Unit) {
  private val element = textarea(initialValue, onkeyup := { _: Event => onChange(text()) }, width := 400, height := 400).render

  def apply(): html.TextArea = element

  def text(): List[String] = rawText().split("\n").toList

  def rawText(): String = element.value

  def `:=`(newValue: String): Unit = element.value = newValue
}
