package scalajs.hello

import org.scalajs.dom._
import scalatags.JsDom.all._

import scala.scalajs.js.Date

object AppEntryPoint extends App {
  val persistedAsciiArt = PersistedText("ascii-art", defaultValue = AsciiArt.cat)
  val canvas = new AsciiArtCanvas()
  val textArea = new AsciiArtTextArea(persistedAsciiArt(), text => canvas.draw(text))
  val lastSavedTimestamp = span().render

  val pageLayout = div(paddingLeft := 20,
    div(float.left, padding := 10,
      p("Ascii Art"),
      textArea()
    ),
    div(float.left, padding := 10,
      p("Image"),
      canvas()
    ),
    div(span("Last saved: "), lastSavedTimestamp, clear.both, fontSize := 12),
    a("Restore the original Ascii Art", href := "#", onclick := { e: Event => reset() }, fontSize := 12)
  )

  window.onload = { _: Event =>
    document.body.appendChild(pageLayout.render)
    window.setInterval(() => saveAsciiArt(), 500)
    canvas.draw(textArea.text())
  }

  private def saveAsciiArt(): Unit = {
    persistedAsciiArt := textArea.rawText()
    lastSavedTimestamp.textContent = new Date().toUTCString()
  }

  private def reset(): Unit = {
    textArea := AsciiArt.cat
    canvas.draw(textArea.text())
  }
}
