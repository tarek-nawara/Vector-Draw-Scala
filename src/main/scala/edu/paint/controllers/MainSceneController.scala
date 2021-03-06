package edu.paint.controllers

import java.awt.geom.Point2D
import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.canvas.Canvas
import javafx.scene.control.Alert.AlertType
import javafx.scene.control._

import edu.paint.model.Circle
import edu.paint.model.engine.DrawEngineImp

/** Main GUI Controller
  *
  *  @author Tarek Nawara
  */
class MainSceneController extends Initializable {
  @FXML
  private var canvas: Canvas = _
  @FXML
  private var drawButton: Button = _
  @FXML
  private var xTextField: TextField = _
  @FXML
  private var yTextField: TextField = _
  @FXML
  private var widthTextField: TextField = _
  @FXML
  private var heightTextField: TextField = _
  @FXML
  private var strokColorPicker: ColorPicker = _
  @FXML
  private var fillColorPicker: ColorPicker = _
  @FXML
  private var warningLabel: Label = _

  private val engine = new DrawEngineImp()

  def initialize(url: URL, resourceBundle: ResourceBundle): Unit = {
  }

  def drawShapes(): Unit = {
    try {
      val position = new Point2D.Double(xTextField.getText.toDouble, yTextField.getText.toDouble)
      val properties = Map("width" -> widthTextField.getText.toDouble, "height" -> heightTextField.getText.toDouble)
      val shape = Circle(position, properties)
      shape.strockColor = strokColorPicker.getValue
      shape.fillColor = fillColorPicker.getValue
      engine.addShape(shape)
      engine.refresh(canvas.getGraphicsContext2D)
    } catch {
      case ex: Throwable => warningLabel.setText("Error happend")
    }
  }

  def undo(): Unit = {
    engine.undo()
    val gc = canvas.getGraphicsContext2D
    gc.clearRect(0, 0, canvas.getWidth, canvas.getHeight)
    engine.refresh(gc)
  }

  def redo(): Unit = {
    engine.redo()
    val gc = canvas.getGraphicsContext2D
    gc.clearRect(0, 0, canvas.getWidth, canvas.getHeight)
    engine.refresh(gc)
  }

  def save(): Unit = {
    engine.save("resources/shapes.json")
    val alert = new Alert(AlertType.INFORMATION)
    alert.setTitle("Information Dialog")
    alert.setContentText("Saved Successfully")
    alert.showAndWait()
  }

  def load(): Unit = {
    engine.load("resources/shapes.json")
    engine.refresh(canvas.getGraphicsContext2D)
  }
}