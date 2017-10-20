package drawing

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

class Graph(val w: Double, h: Double) extends Canvas(w, h) {
  private val g = super.graphicsContext2D

  private var xBegin = -10.0
  private var xEnd = 10.0

  private var yBegin = -10.0
  private var yEnd = 10.0

  private var xScale = 1.0f
  private var yScale = 1.0f

  private var widthPerPixel = (xEnd - xBegin) / this.getWidth
  private var heightPerPixel = (yEnd - yBegin) / this.getHeight

  private var points: Seq[Point] = Seq()

  g.setFill(Color.White)

  def setScale(x: Float, y: Float): Unit = {
    xScale = x
    yScale = y
  }

  def setXFrame(xBegin: Float, xEnd: Float): Unit = {
    this.xBegin = xBegin
    this.xEnd = xEnd
    this.widthPerPixel = (xEnd - xBegin) / this.getWidth
  }

  def setYFrame(yBegin: Float, yEnd: Float): Unit = {
    this.yBegin = yBegin
    this.yEnd = yEnd
    this.heightPerPixel = (yEnd - yBegin) / this.getHeight
  }

  private def drawVerticalLines(): Unit = {
    var bound = xBegin
    g.stroke = Color.Grey
    while (bound < xEnd) {
      val conv = convertHorizontalCoordinate(bound)
      g.strokeLine(conv, 0, conv, this.height.toDouble)
      bound = bound + xScale
    }
    this.g.stroke = Color.Black
  }

  private def drawHorizontalLines(): Unit = {
    var bound = yBegin
    g.stroke = Color.Gray
    while (bound < yEnd) {
      val conv = convertVerticalCoordinate(bound)
      g.strokeLine(0, conv, this.width.toDouble, conv)
      bound = bound + yScale
    }
    g.stroke = Color.Black
  }

  private def drawAxis(): Unit = {
    g.stroke = Color.Blue
    val xConv = convertHorizontalCoordinate(0)
    val yConv = convertVerticalCoordinate(0)
    this.g.strokeLine(xConv, 0, xConv, height.toDouble)
    this.g.strokeLine(0, yConv, width.toDouble, yConv)
    g.stroke = Color.Black
  }

  private def drawBorder(): Unit = {
    this.g.stroke = Color.Gray
    this.g.strokeRect(0, 0, this.width.toDouble, this.height.toDouble)
    this.g.stroke = Color.Black
  }

  def setPoints(points: Seq[Point]): Seq[Point] = {
    this.points = points.sorted
    this.points
  }

  def redraw(): Unit = {
    drawBorder()
    drawVerticalLines()
    drawHorizontalLines()
    drawAxis()
    if (this.points.isEmpty)
      return
    var (xPrev, yPrev) = convertCoordinate(this.points.head)
    if (xPrev < 0 || xPrev > this.getWidth) {
      xPrev = if (xPrev < 0) 0 else this.getWidth
    }
    if (yPrev < 0 || yPrev > this.getHeight) {
      yPrev = if (yPrev < 0) 0 else this.getHeight
    }
    for (point: Point <- this.points) {
      val (xCoordinate, yCoordinate) = convertCoordinate(point)
      if (xCoordinate >= 0 && xCoordinate <= this.getWidth &&
        yCoordinate >= 0 && yCoordinate <= this.getHeight) {
        this.g.strokeLine(xPrev, yPrev, xCoordinate, yCoordinate)
        xPrev = xCoordinate
        yPrev = yCoordinate
      }
      else {
        if (xPrev < 0 || xPrev > this.getWidth) {
          xPrev = if (xCoordinate < 0) 0 else this.getWidth
        }
        if (yPrev < 0 || yPrev > this.getHeight) {
          yPrev = if (yCoordinate < 0) 0 else this.getHeight
        }
      }

    }
  }

  private def convertHorizontalCoordinate(x: Double): Double = {
    (x - xBegin) / widthPerPixel
  }

  private def convertVerticalCoordinate(y: Double): Double = {
    height.toDouble - (y - yBegin) / heightPerPixel
  }

  def convertCoordinate(point: Point): (Double, Double) = {
    val height = this.getHeight

    val widthPerPixel = this.widthPerPixel
    val heightPerPixel = this.heightPerPixel

    ((point.x - xBegin) / widthPerPixel,
      height - ((point.y - yBegin) / heightPerPixel))
  }
}
