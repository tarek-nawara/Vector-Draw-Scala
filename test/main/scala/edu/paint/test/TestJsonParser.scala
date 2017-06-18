package edu.paint.test

import edu.paint.parsers._

object TestJsonParser {
  def main(args: Array[String]): Unit = {
    val parser = new JsonParser("resources/shapes.json")
    val res = parser.loadShapes()
    println(res)
  }
}