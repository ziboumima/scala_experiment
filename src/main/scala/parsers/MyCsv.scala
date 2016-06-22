package parsers
import java.io.File

import com.github.tototoshi.csv._

/**
  * Created by nnguyen on 15/06/16.
  */
object MyCsv {

  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("/home/nnguyen/workspaces/ica-finlib/cli/src/main/resources/BENCHMARK500_stable.csv"))
    println(reader.all())
    reader.close()
  }

}
