package edu.paint.model.serializers

import scala.collection.mutable.ListBuffer
import edu.paint.model.Shape
import java.io.PrintWriter
import java.io.File
import java.awt.geom.Point2D
import javafx.scene.paint.Color

/** Implementation of the class responsible
  * for serializing the shapes into its equivalent
  * json representation.
  *
  *
  * Note this class doesn't serialize every thing but only the needed
  * fields to restore the shape like its position .. etc
  *
  * @param path path to serialize the shapes
  * @author Tarek Nawara
  */
class JsonSerializer(path: String) {
  /** Given a list of shapes it will serialize it
    * and save it in the given file
    *
    * @param shapes list of shapes to save
    */
  def serialize(shapes: ListBuffer[Shape]): Unit = {
    def serialize(shape: Shape): String = {
      val sb = new StringBuilder()
      sb.append("{")
      sb.append(serializeClass(shape.getClass)).append(",")
      sb.append(serializePosition(shape.position)).append(",")
      sb.append(serializeFillColor(shape.fillColor)).append(",")
      sb.append(serializeStrokColor(shape.strockColor)).append(",")
      sb.append(serializeProperties(shape.properties))
      sb.append("}")
      sb.toString()
    }

    def serializeClass(c: Class[_]): String = "\"class\":" + "\"" + c.getName + "\""

    def serializeProperties(properties: Map[_, _]): String =
      "\"properties\": {" + properties.map {
        case (key, value) =>
          "\"" + key + "\":" + value
      }.mkString(",") + "}"

    def serializePosition(position: Point2D.Double): String =
      "\"position\": {" + "\"x\":" + position.x + ",\"y\":" + position.y + "}"

    def serializeFillColor(color: Color): String = "\"fillColor\":" + "\"" + color + "\""

    def serializeStrokColor(color: Color): String = "\"strokColor\":" + "\"" + color + "\""

    val out = new PrintWriter(new File(path))
    out.println("[")
    out.print(shapes.map(serialize).mkString(","))
    out.println("]")
    out.close()
  }
}