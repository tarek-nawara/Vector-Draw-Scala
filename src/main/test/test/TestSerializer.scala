package test

import edu.paint.model.Circle
import java.awt.geom.Point2D
import edu.paint.model.Rectangle
import edu.paint.model.serializers.JsonSerializer
import scala.collection.mutable.ListBuffer

object TestSerializer {
  def main(args: Array[String]): Unit = {
    val properties = Map("width" -> 100.0, "height" -> 100.0)
    val circle = new Circle(new Point2D.Double(10.0, 10.0), properties)
    val rectangle = new Rectangle(new Point2D.Double(10.0, 10.0), properties)
    val serializer = new JsonSerializer("resources/shapes.json")
    serializer.serialize(ListBuffer(circle, rectangle))
  }
}