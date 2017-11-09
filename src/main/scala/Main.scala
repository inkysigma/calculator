import java.io.IOException

import drawing.Graph
import tree.ExpressionTree

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Rectangle2D
import scalafx.scene.Scene
import scalafx.stage.Screen
import scalafxml.core.{FXMLView, NoDependencyResolver}

object Main extends JFXApp {
  val graph = new Graph()
  graph.setSize(100, 100)

  private val rootResource = getClass.getResource("views/Graph.fxml")

  if (rootResource == null) {
    throw new IOException("Unable to locate")
  }

  val root = FXMLView(rootResource, NoDependencyResolver)

  stage = new PrimaryStage {
    title = "Graphing Calculator"
    scene = new Scene(new javafx.scene.Scene(root))
  }

  val tree = new ExpressionTree
  tree.parseString("x")
  graph.addPlot("Something", tree.evaluate(-10, 10))
  graph.redraw()

  val bounds: Rectangle2D = Screen.primary.visualBounds

  stage.setHeight(bounds.getWidth / 2)
  stage.setWidth(bounds.getHeight / 0.75)

  stage.show()

  stage.setX((bounds.getWidth - stage.getWidth) / 2)
  stage.setY((bounds.getHeight - stage.getHeight) / 2)
}
