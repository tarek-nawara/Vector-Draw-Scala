package edu.paint.model


import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import java.awt.geom.Point2D

/** Representation of the shapes drawn over the canvas
  *
  * @author Tarek Nawara
  */
trait Shape {
  def position: Point2D.Double

  def properties: Map[String, Double]

  var strockColor: Color = Color.BLACK
  var fillColor: Color = Color.WHITE

  def draw(canvas: GraphicsContext): Unit
}

case class Circle(position: Point2D.Double, properties: Map[String, Double]) extends Shape {
  override def draw(canvas: GraphicsContext): Unit = {
    canvas.setStroke(this.strockColor)
    canvas.setFill(fillColor)
    canvas.fillArc(position.x, position.y, properties("width"),
      properties("height"), 360, 360, ArcType.OPEN)
    canvas.strokeArc(position.x, position.y, properties("width"), properties("height"), 360, 360, ArcType.OPEN)
  }
}

case class Rectangle(position: Point2D.Double, properties: Map[String, Double])
  extends Shape {
  override def draw(canvas: GraphicsContext): Unit = {
    canvas.setStroke(strockColor)
    canvas.setFill(fillColor)
    canvas.fillRect(position.x, position.y, properties("width"), properties("height"))
    canvas.strokeRect(position.x, position.y, properties("width"), properties("heigth"))
  }
}