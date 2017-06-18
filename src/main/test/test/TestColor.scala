package test

import javafx.scene.paint.Color

object TestColor {
  def main(args: Array[String]): Unit = {
    val color = Color.BEIGE
    val other = Color.web(color.toString())
    println(color)
    println(other)
  }
}