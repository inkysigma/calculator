package drawing

class Point(val x: Double, val y: Double) extends Ordered[Point] {

  def ~=(x: Double, y: Double, precision: Double) : Boolean = {
    if ((x - y).abs < precision) true else false
  }

  override def compare(that: Point): Int = {
    val bool = this.x > that.x
    if (bool) {
      1
    } else if (~=(this.x, that.x, 0.00001)) {
      0
    } else {
      -1
    }
  }
}
