package scalajs.hello

import org.scalajs.dom._
import scalatags.JsDom.all.{canvas, _}

import scala.scalajs.js.Date

object AppEntryPoint extends App with AsciiArt {
  val persistedAsciiArt = PersistedText("ascii-art", cat)
  val canvasElement = canvas(id := "canvas", width := 200, height := 200).render
  val textArea = textarea(persistedAsciiArt(), onkeyup := { _: Event => redraw() }, width := 400, height := 400).render
  val lastSavedTime = span().render
  val pageLayout = div(paddingLeft := 20,
    div(float.left, padding := 10,
      p("Ascii Art"),
      textArea
    ),
    div(float.left, padding := 10,
      p("Image"),
      canvasElement
    ),
    div(span("Last saved: "), lastSavedTime, clear.both, fontSize := 12),
    a("Restore the original Ascii Art", href := "#", onclick := { e: Event => reset() }, fontSize := 12)
  )

  window.onload = { _: Event =>
    document.body.appendChild(pageLayout.render)
    window.setInterval(() => saveAsciiArt(), 500)
    redraw()
  }

  private def redraw(): Unit = {
    val lines = textArea.value.split("\n")
    val ctx = canvasElement.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    ctx.clearRect(0, 0, 400, 200)

    for ((line, lineNumber) <- lines.zipWithIndex; (char, charNumber) <- line.zipWithIndex) {
      ctx.fillStyle = if (char != ' ') "black" else "white"
      ctx.fillRect(charNumber * 10, lineNumber * 10, 10, 10)
    }
  }

  private def saveAsciiArt(): Unit = {
    persistedAsciiArt := textArea.value
    lastSavedTime.textContent = new Date().toUTCString()
  }

  private def reset(): Unit = {
    textArea.value = cat
    redraw()
  }
}
