package controller

import javafx.fxml.FXML

import com.jfoenix.controls.JFXTextField
import drawing.Graph
import tree.ExpressionTree

import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class GraphController(@FXML graph: Graph,
                      @FXML functionInput: JFXTextField) {
  val tree = new ExpressionTree()

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
  def changeDomain(e: ActionEvent): Unit = {
    val domain = graph.getChart.getXYPlot.getDomainAxis
    val seq = tree.evaluate(domain.getLowerBound - 10, domain.getUpperBound + 10)
    graph.addPlot("f(x)", seq)
    graph.redraw()
  }
}
