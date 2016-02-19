import monoid.Toto
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers


trait Get[A, B] {
  def get(a: A): B
}

trait GetInt extends Get[Int, String]{
  def get(a: Int) = a.toString
}

trait GetString extends Get[String, Int]{
  def get(a: String) = 1
}


class HelloSpec extends FlatSpec with ShouldMatchers {
  "Hello" should "have tests" in {
    true should be === true
  }
  "Count" should "do the job" in {
    Toto.count("this should be good") should be === 4
  }
}
