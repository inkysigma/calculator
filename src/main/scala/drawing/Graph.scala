package drawing

import org.jfree.chart.ChartFactory
import org.jfree.chart.fx.ChartViewer
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}

import scala.collection.mutable

class Graph extends
  ChartViewer(ChartFactory.createXYLineChart("", "", "", new XYSeriesCollection())) {

  def setSize(w: Double, h: Double): Unit = {
    super.setWidth(w)
    super.setHeight(h)
  }

  private def width: Double = super.getWidth
  private def height: Double = super.getHeight

  private var xBegin = -10.0
  private var xEnd = 10.0

  private var yBegin = -10.0
  private var yEnd = 10.0

  private var xScale = 1.0f
  private var yScale = 1.0f

  private var widthPerPixel = (xEnd - xBegin) / width
  private var heightPerPixel = (yEnd - yBegin) / height

  private val dataset = new XYSeriesCollection()
  private val series = mutable.HashMap[String, XYSeries]()


  def setScale(x: Float, y: Float): Unit = {
    super.setScaleX(x)
    super.setScaleY(y)
  }

  def setXFrame(xBegin: Float, xEnd: Float): Unit = {
    super.getChart.getXYPlot.getDomainAxis.setRange(xBegin, xEnd)
    this.xBegin = xBegin
    this.xEnd = xEnd
    this.widthPerPixel = (xEnd - xBegin) / width
  }

  def setYFrame(yBegin: Float, yEnd: Float): Unit = {
    super.getChart.getXYPlot.getRangeAxis.setRange(yBegin, yEnd)
    this.yBegin = yBegin
    this.yEnd = yEnd
    this.heightPerPixel = (yEnd - yBegin) / height
  }

  def addPlot(name: String, points: Seq[Point]): Unit = {
    val series = new XYSeries(name)
    this.series.put(name, series)
    points.foreach(f => series.add(f.x, f.y))
    dataset.addSeries(series)
  }

  def removePlot(name: String): Unit = {
    series.get(name) match {
      case Some(s) =>
        dataset.removeSeries(s)
        series.remove(name)
      case None =>
    }
  }

  def redraw(): Unit = {
    super.setChart(ChartFactory.createXYLineChart("", "", "", dataset))
  }
}
