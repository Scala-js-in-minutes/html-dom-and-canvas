package scalajs.hello

import org.scalajs.dom.{html, _}
import scalatags.JsDom.all.{canvas, height, id, width, _}

class AsciiArtCanvas {
  private val element = canvas(id := "canvas", width := 200, height := 200).render

  def apply(): html.Canvas = element

  def draw(text: List[String]): Unit = {
    val ctx = element.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    ctx.clearRect(0, 0, 400, 200)

    for ((line, lineNumber) <- text.zipWithIndex; (char, charNumber) <- line.zipWithIndex) {
      ctx.fillStyle = if (char != ' ') "black" else "white"
      ctx.fillRect(charNumber * 10, lineNumber * 10, 10, 10)
    }
  }
}
