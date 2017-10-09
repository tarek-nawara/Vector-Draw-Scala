package edu.paint.model.parsers

import java.awt.geom.Point2D
import java.io.{File, FileInputStream}
import javafx.scene.paint.Color

import edu.paint.model.Shape

import scala.collection.mutable.ListBuffer
import scala.io.Source

/** Parser that parses a Json representation of the shapes
  * and returns all the shapes.
  *
  * @author Tarek Nawara
  */
class JsonParser(path: String) {
  /**
    * Load all the shapes from the given file
    */
  def loadShapes(): ListBuffer[Shape] = {
    val inputStream = new FileInputStream(new File(path))
    val data = Source.fromInputStream(inputStream).getLines().mkString("\n")
    val res = for {
      Some(a: List[Map[String, Any]]) <- List(JSON.parseFull(data))
      map <- a
    } yield buildShape(map)
    ListBuffer(res: _*)
  }

  private def buildShape(map: Map[String, Any]): Shape = {
    val classPath = map("class").asInstanceOf[String]
    val position = map("position").asInstanceOf[Map[String, Any]]
    val x = position("x").asInstanceOf[Double]
    val y = position("y").asInstanceOf[Double]
    val temp = map("properties").asInstanceOf[Map[String, Any]]
    val properties = temp.mapValues(_.asInstanceOf[Double])
    val constructor = Class.forName(classPath).getConstructor(classOf[Point2D.Double], classOf[Map[String, Double]])
    val res = constructor.newInstance(new Point2D.Double(x, y), properties).asInstanceOf[Shape]
    res.fillColor = Color.web(map("fillColor").asInstanceOf[String])
    res.strockColor = Color.web(map("strokColor").asInstanceOf[String])
    res
  }
}