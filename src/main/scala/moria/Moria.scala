package moria

import processing.core._
import processing.event.MouseEvent

class Moria extends PApplet {
  val BoardWidth = 1024
  val BoardHeight = 512
  var cPosX = 64.0f
  var cPosY = 64.0f
  var r1 =
    List(Room(32, 32, 64, 128), Room(128, 32, 64, 128), Room(32, 256, 64, 32))

  override def settings(): Unit = {
    size(BoardWidth, BoardHeight)
  }

  override def draw(): Unit = {
    background(5, 0, 155)
    r1.foreach { room =>
      room.drawRoom(this)
    }
    ellipse(cPosX, cPosY, 16, 16)
    ellipse(mouseX, mouseY, 4, 4)
    color(255, 55, 55)
  }

  override def mouseClicked(event: MouseEvent): Unit = {
    if (r1.exists(r => r.isInside(event.getX, event.getY))) {
      cPosX = ((event.getX / 16).floor) * 16
      cPosY = ((event.getY / 16).ceil) * 16
    }
  }
}

object Moria extends App {
  PApplet.main(classOf[Moria].getName)
}
