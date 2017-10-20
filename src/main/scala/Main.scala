import drawing.Graph
import tree.ExpressionTree

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Rectangle2D}
import scalafx.scene.Scene
import scalafx.scene.layout.{FlowPane, StackPane}
import scalafx.scene.paint.Color._
import scalafx.stage.Screen

object Main extends JFXApp {
  val graph = new Graph(500, 400)

  val mainPane =  new FlowPane

  val graphPane = new StackPane
  graphPane.children.add(graph)
  graphPane.margin = Insets(25)

  mainPane.children.add(graphPane)

  stage = new PrimaryStage {
    title = "Graphing Calculator"
    scene = new Scene {
      fill = LightGray
      content = mainPane
    }
  }

  val tree = new ExpressionTree
  tree.parseString("x")
  graph.setPoints(tree.evaluate(-10, 10))
  graph.redraw()

  val bounds: Rectangle2D = Screen.primary.visualBounds

  stage.setHeight(bounds.getWidth / 2)
  stage.setWidth(bounds.getHeight / 0.75)

  stage.show()


  stage.setX((bounds.getWidth - stage.getWidth) / 2)
  stage.setY((bounds.getHeight - stage.getHeight) / 2)
}
