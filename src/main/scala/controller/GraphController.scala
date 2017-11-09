package controller

import javafx.fxml.FXML

import com.jfoenix.controls.JFXTextField

import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml(additionalControls=List("com.jfoenix.controls"))
class GraphController(functionInput: JFXTextField,
                      xMin: JFXTextField,
                      xMax: JFXTextField) {

  @FXML
  def addFunction(event: ActionEvent): Unit = {
    println("HELLO")
  }
}
