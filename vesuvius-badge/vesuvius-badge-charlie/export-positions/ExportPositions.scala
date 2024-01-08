

object ExportPositions extends App {
  /*
  Convert csv lines like this:
  "D1","LED white","LED_0402_1005Metric",132.300000,-107.350000,180.000000,top
  "D2","LED white","LED_0402_1005Metric",140.550000,-106.850000,0.000000,top
  "D3","LED white","LED_0402_1005Metric",145.300000,-106.600000,0.000000,top
  "D4","LED white","LED_0402_1005Metric",157.285000,-106.350000,0.000000,top

  to a C struct with fixnum positions

  struct led_position {
    uint16 x;
    uint16 y;
  }
  struct led_position led_positions[] = {
    {13230, -10735},
    {14000, -10685},
    {14530, -10660},
    {15729, -10635},
  }
   */

  val lines = io.Source.fromFile("../gerbers/vesuvius-badge-charlie-top-pos.csv").getLines()
  val LineR = """"D(\d+)","[^"]*","[^"]*",([0-9\-.]+),([0-9\-.]+),[^,]+,top""".r

  val positions = lines.collect {
    case LineR(idx, x, y) =>
//      println(s"idx=$idx x=$x y=$y")
      (idx, x.toDouble, -y.toDouble)
  }.toVector

  val minX = positions.map(_._2).min
  val minY = positions.map(_._3).min

  positions.foreach { case (idx, x, y) =>
    println(f"  {${(x - minX) * 100}%.0f, ${(y - minY) * 100}%.0f}, // $idx")

  }
}