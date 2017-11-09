import javafx.application.Application

import drawing.{Graph, Point}
import org.scalatest.{FlatSpec, Matchers}

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.Stage

class GraphTest extends FlatSpec with Matchers {
  val graph: Graph = new Graph
  graph.setSize(100, 100)

  "The graph" should "redraw points in a sorted order" in {
    graph.addPlot("something", Seq(new Point(10, 10),
      new Point(8, 8),
      new Point(9, 9),
      new Point(7, 7)))
    graph.redraw()
    print("Hello")
  }

  it should "do nothing" in {

  }
}
