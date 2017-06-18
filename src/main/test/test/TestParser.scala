package test

import edu.paint.model.parsers.JsonParser
import java.io.FileInputStream
import scala.io.Source
import java.io.File
import scala.util.parsing.json.JSON
import java.awt.geom.Point2D

object TestParser {
  def main(args: Array[String]): Unit = {
    val inputStream = new FileInputStream(new File("resources/shapes.json"))
    val data = Source.fromInputStream(inputStream).getLines().mkString("\n")
    val res = JSON.parseFull(data)
    println(res)
    
    val parser = new JsonParser("resources/shapes.json")
    val shapes = parser.loadShapes()
    println(shapes)
  }
}