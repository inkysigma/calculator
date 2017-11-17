package controller

import javafx.fxml.FXML

import com.jfoenix.controls.JFXTextField
import drawing.Graph
import tree.ExpressionTree

import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class GraphController(@FXML graph: Graph,
                      @FXML functionInput: JFXTextField,
                      @FXML xMin: JFXTextField,
                      @FXML xMax: JFXTextField,
                      @FXML yMin: JFXTextField,
                      @FXML yMax: JFXTextField) {
  val tree = new ExpressionTree()

  def parseDouble(s: String) = try { Some(s.toDouble) } catch { case _ => None }

  @FXML
  def addFunction(e: ActionEvent): Unit = {
    val text = functionInput.textProperty().get()
    print(text)
    tree.parseString(text)
    val domain = graph.getChart.getXYPlot.getDomainAxis
    val seq = tree.evaluate(domain.getLowerBound - 10, domain.getUpperBound + 10)
    graph.addPlot("f(x)", seq)
    graph.redraw()
  }

  @FXML
  def resizeXFrame(e: ActionEvent): Unit = {
    var xBegin: Double = 0.0
    var xEnd: Double = 0.0
    parseDouble(this.xMin.textProperty().get()) match {
      case Some(d) => xBegin = d
      case None => return
    }
    parseDouble(this.xMax.textProperty().get()) match {
      case Some(d) => xEnd = d
      case None => return
    }
    graph.setXFrame(xBegin, xEnd)
    graph.redraw()
  }

  @FXML
  def resizeYFrame(e: ActionEvent): Unit = {
    var yBegin: Double = 0.0
    var yEnd: Double = 0.0
    parseDouble(this.yMin.textProperty().get()) match {
      case Some(d) => yBegin = d
      case None => return
    }
    parseDouble(this.yMax.textProperty().get()) match {
      case Some(d) => yEnd = d
      case None => return
    }
    graph.setYFrame(yBegin, yEnd)
    graph.redraw()
  }

  @FXML
  def changeDomain(e: ActionEvent): Unit = {
    val domain = graph.getChart.getXYPlot.getDomainAxis
    val seq = tree.evaluate(domain.getLowerBound - 10, domain.getUpperBound + 10)
    graph.addPlot("f(x)", seq)
    graph.redraw()
  }
}
