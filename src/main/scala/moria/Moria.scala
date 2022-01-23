package moria

import processing.core._
import processing.event.{KeyEvent, MouseEvent}

class Moria extends PApplet {

  var time: Long = System.currentTimeMillis
  val BoardWidth = 1024
  val BoardHeight = 512
  var doneNothing = false

  override def setup(): Unit = {}

  override def settings(): Unit = {
    size(BoardWidth, BoardHeight)

  }

  override def draw(): Unit = {

    background(100, 100, 100)
    fill(255, 255, 255)

    World.rooms.foreach { room =>
      room.draw(this)
    }

    stroke(0, 0, 0)

    World.enemies.foreach { enemy => enemy.draw(this) }
    World.player.draw(this)

    fill(255, 255, 0, 75)

    rect((mouseX / 16).ceil * 16, (mouseY / 16).ceil * 16, 16, 16)
    if (World.isMenu) {
      println("Menu")
      rect(4, 4, 1016, 504)
      fill(255, 255, 0)
      rect(8, 8, 496, 496)
      rect(520, 8, 496, 496)
      fill(0, 0, 0)
      text(
        s" STR: ${World.player.Strength}(${World.player.bStrength}) \n FIN: ${World.player.Finesse}(${World.player.bFinesse}) \n FRT: ${World.player.Fortitude}(${World.player.bFortitude}) \n ENG: ${World.player.Energy}(${World.player.bEnergy}) \n INT: ${World.player.Intelligence}(${World.player.bIntelligence}) \n PRS: ${World.player.Persuasion}(${World.player.bPersuasion}) \n IMD: ${World.player.Intimidation}(${World.player.bIntimidation}) \n RSM: ${World.player.Rationalism}(${World.player.bRationalism}) \n PCP: ${World.player.Perception}(${World.player.bPerception})",
        540,
        28
      )
    } else {
      Update(.2f)
    }

  }

  def Update(tTime: Float): Unit = {
    val currentTime = System.currentTimeMillis

    if (currentTime - time > tTime * 1000) {
      val moved = Navigation.navigateObject(
        World.player
      )

      if (moved || doneNothing) {
        println(s"Moved $moved done nothing $doneNothing")
        World.enemies.foreach(enemy => enemy.chooseState())

        World.enemies.foreach(enemy => Navigation.navigateObject(enemy))

      }
      time = currentTime
      doneNothing = false

    }

    World.checkForDead()
    World.player.checkForDead()

  }

  override def mousePressed(event: MouseEvent): Unit = {
    World.player.pClick(mouseX, mouseY)
  }

  override def keyPressed(event: KeyEvent): Unit = {
    World.player.pressKey(keyCode)
    if (key == ' ') {
      doneNothing = true
    }
    if (key == 'i' && !World.isMenu) { World.isMenu = true }
    else if (key == 'i' && World.isMenu) { World.isMenu = false }
  }

}

object Moria extends App {

  PApplet.main(classOf[Moria])
}
