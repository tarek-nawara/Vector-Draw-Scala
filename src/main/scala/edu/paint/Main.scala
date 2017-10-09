package edu.paint

import scalafx.application.JFXApp
import scalafx.scene.Scene
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage

object Main extends JFXApp {
  val root: Parent = FXMLLoader.load(getClass.getResource("views/editor/Editor.fxml"))
  stage = new PrimaryStage() {
    title = "Paint"
    scene = new Scene(root) {
    	stylesheets += getClass.getResource("views/editor/properties.css").toExternalForm()
    }
  }
}