package edu.paint.model.engine

import javafx.scene.canvas.GraphicsContext
import scala.collection.mutable.ListBuffer
import edu.paint.model.Shape

trait DrawEngine {
  /**
   * Referesh the contents of the canvas
   * @param canvas the view canvas where the shapes should be drawn over
   */
  def refresh(canvas: GraphicsContext)

  /**
   * Add a new shape to the canvas
   * @param shape shape to add
   */
  def addShape(shape: Shape)

  /**
   * Remove shape from the canvas
   * @param shape shape to remove
   */
  def removeShape(shape: Shape)

  /**
   * Update shape drawn on the canvas
   * @param old shape the shape already drawn over the canvas
   * @param new shape the updated shape
   */
  def updateShape(oldShape: Shape, newShape: Shape)

  /**
   * Get list of all the shapes drawn on the canvas
   */
  def getShapes(): ListBuffer[Shape]

  /**
   * Undo last action performed
   */
  def undo(): Unit

  /**
   * Redo last action performed
   */
  def redo(): Unit

  /**
   * save the shapes of the application in a file
   * <p>
   * The file formats could be `json` or `xml`
   * </p>
   * @param path path to the file to save
   */
  def save(path: String)

  /**
   * load the shapes from a file
   * <p>
   * The file formats could be `json` or `xml`
   * </p>
   * @param path file path
   */
  def load(path: String)
}