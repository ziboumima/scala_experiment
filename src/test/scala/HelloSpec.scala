import monoid.Toto
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class HelloSpec extends FlatSpec with ShouldMatchers {
  "Hello" should "have tests" in {
    true should be === true
  }
  "Count" should "do the job" in {
    Toto.count("this should be good") should be === 4
  }
}
