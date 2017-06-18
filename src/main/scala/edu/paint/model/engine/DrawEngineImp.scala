package edu.paint.model.engine

import java.util.Stack

import scala.collection.mutable.ListBuffer

import edu.paint.model.Shape
import javafx.scene.canvas.GraphicsContext
import edu.paint.model.serializers.JsonSerializer
import edu.paint.model.parsers.JsonParser

/**
 * Implementation for the drawing engine
 *  @author Tarek Nawara
 */
class DrawEngineImp extends DrawEngine {

  /* Representation of an action taken by the user
   * the action manipulate the state of the engine
   * and is responsible for reversing its effect
   * and reproduce it again.
  */
  private sealed trait Action {
    def undo(engine: DrawEngineImp): Unit
    def redo(engine: DrawEngineImp): Unit
  }

  private case class AddShapeAction(shape: Shape) extends Action {
    override def undo(engine: DrawEngineImp): Unit = engine.shapes -= shape
    override def redo(engine: DrawEngineImp): Unit = engine.shapes += shape
  }

  private case class RemoveShapeAction(shape: Shape) extends Action {
    override def undo(engine: DrawEngineImp): Unit = engine.shapes += shape
    override def redo(engine: DrawEngineImp): Unit = engine.shapes -= shape
  }

  private case class UpdateShapeAction(oldShape: Shape, newShape: Shape) extends Action {
    override def undo(engine: DrawEngineImp): Unit = {
      val index = engine.shapes.indexOf(newShape)
      engine.shapes(index) = oldShape
    }
    override def redo(engine: DrawEngineImp): Unit = {
      val index = engine.shapes.indexOf(oldShape)
      engine.shapes(index) = oldShape
    }
  }

  private val undoStack = new Stack[Action]()
  private val redoStack = new Stack[Action]()
  private val shapes = new ListBuffer[Shape]()

  def addShape(shape: Shape): Unit = {
    redoStack.clear()
    val action = AddShapeAction(shape)
    undoStack.push(action)
    shapes += shape
  }

  def updateShape(oldShape: Shape, newShape: Shape): Unit = {
    redoStack.clear()
    val action = UpdateShapeAction(oldShape, newShape)
    val index = shapes.indexOf(oldShape)
    shapes(index) = newShape
  }

  def removeShape(shape: Shape): Unit = {
    // TODO undo should insert the shape in the proper position
    redoStack.clear()
    val index = shapes.indexOf(shape)
    val action = RemoveShapeAction(shape)
    shapes -= shape
  }

  def getShapes(): ListBuffer[Shape] = shapes

  def refresh(canvas: GraphicsContext): Unit = for (shape <- shapes) shape.draw(canvas)

  def redo(): Unit = {
    if (!redoStack.isEmpty()) {
      val action = redoStack.pop()
      undoStack.push(action)
      action.redo(this)
    }
  }

  def undo(): Unit = {
    if (!undoStack.isEmpty()) {
      val action = undoStack.pop()
      redoStack.push(action)
      action.undo(this)
    }
  }

  def save(path: String): Unit = {
    val serializer = new JsonSerializer(path)
    serializer.serialize(shapes)
  }

  def load(path: String): Unit = {
    val parser = new JsonParser(path)
    val temp = parser.loadShapes()
    shapes.clear()
    shapes ++= temp
  }
}
