package regex



object Demo {
  def main(args: Array[String]) {
    val pattern = "\\A(S|s)cala"
    val datePattern = "(\\A\\d{2}\\.\\d{2}\\.\\d{4})"
    val periodPattern = "\\d{1,2}[DMY]"
    val rangePattern = s"((?:$periodPattern)|(?:$datePattern)):((?:$periodPattern)|(?:$datePattern)):($periodPattern)"
    val str = "Scala is scalable and cool"
    val date = "01.02.2015"

    date match {
      case datePattern.r(x) => print(x)
    }

    println(datePattern.r.unapplySeq(date).isDefined)
    println(periodPattern.r.unapplySeq("12Y").isDefined)
    println(rangePattern.r.unapplySeq("1M:2D:3M").isDefined)
    println(rangePattern.r.unapplySeq("1M:2D:3M"))
    println((pattern.r findAllIn str).mkString(","))
    println((datePattern.r findAllIn date).mkString(","))
  }
}