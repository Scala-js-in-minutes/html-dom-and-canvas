package scalajs.hello

import org.scalajs.dom._
import scalatags.JsDom.all._

import scala.scalajs.js.Date

object AppEntryPoint extends App {
  val persistedAsciiArt = PersistedText("ascii-art", AsciiArt.cat)
  val asciiArtCanvas = new AsciiArtCanvas()

  def text: List[String] = textArea.value.split("\n").toList

  val textArea = textarea(persistedAsciiArt(), onkeyup := { _: Event => asciiArtCanvas.redraw(text) }, width := 400, height := 400).render
  val lastSavedTime = span().render
  val pageLayout = div(paddingLeft := 20,
    div(float.left, padding := 10,
      p("Ascii Art"),
      textArea
    ),
    div(float.left, padding := 10,
      p("Image"),
      asciiArtCanvas()
    ),
    div(span("Last saved: "), lastSavedTime, clear.both, fontSize := 12),
    a("Restore the original Ascii Art", href := "#", onclick := { e: Event => reset() }, fontSize := 12)
  )

  window.onload = { _: Event =>
    document.body.appendChild(pageLayout.render)
    window.setInterval(() => saveAsciiArt(), 500)
    asciiArtCanvas.redraw(text)
  }

  private def saveAsciiArt(): Unit = {
    persistedAsciiArt := textArea.value
    lastSavedTime.textContent = new Date().toUTCString()
  }

  private def reset(): Unit = {
    textArea.value = AsciiArt.cat
    asciiArtCanvas.redraw(text)
  }
}
