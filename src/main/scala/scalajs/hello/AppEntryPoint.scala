package scalajs.hello

import org.scalajs.dom._
import scalatags.JsDom.all._

import scala.scalajs.js.Date

object AppEntryPoint extends App {
  val persistedAsciiArt = PersistedText("ascii-art", AsciiArt.cat)
  val canvas = new AsciiArtCanvas()
  val textArea = new AsciiArtTextArea(persistedAsciiArt(), text => canvas.draw(text))

  val lastSavedTime = span().render
  val pageLayout = div(paddingLeft := 20,
    div(float.left, padding := 10,
      p("Ascii Art"),
      textArea()
    ),
    div(float.left, padding := 10,
      p("Image"),
      canvas()
    ),
    div(span("Last saved: "), lastSavedTime, clear.both, fontSize := 12),
    a("Restore the original Ascii Art", href := "#", onclick := { e: Event => reset() }, fontSize := 12)
  )

  window.onload = { _: Event =>
    document.body.appendChild(pageLayout.render)
    window.setInterval(() => saveAsciiArt(), 500)
    canvas.draw(textArea.text())
  }

  private def saveAsciiArt(): Unit = {
    persistedAsciiArt := textArea.rawText()
    lastSavedTime.textContent = new Date().toUTCString()
  }

  private def reset(): Unit = {
    textArea := AsciiArt.cat
    canvas.draw(textArea.text())
  }
}
