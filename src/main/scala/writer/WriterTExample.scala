package writer

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
/**
  * Created by nnguyen on 09/11/16.
  */
object WriterTExample {

  import scalaz._
  import Scalaz._

  def test(): (List[String], Int) = {
    def calc1 = Writer(List("doing calc"), 11)
    def calc2 = Writer(List("doing other"), 22)

    val r = for {
      a <- calc1
      b <- calc2
    } yield {
      a + b
    }
    r.run
  }

  def test1(): WriterT[Future, Map[String, Double], Int] = {

    val doubleMonoid: Monoid[Double] = new Monoid[Double] {
      def zero = 0.0
      def append(a: Double, b: => Double) = a + b
    }

    def immutableMapMonoid[K, V](implicit V: Monoid[V]): Monoid[Map[K, V]] = new Monoid[Map[K, V]] {
      def zero = Map.empty
      def append(m1: Map[K, V], m2: => Map[K, V]): Map[K, V] = {
        m2.foldLeft(m1) {
          case (m, (k, v)) =>
            m + (k -> V.append(m.getOrElse(k, V.zero), v))
        }
      }
    }
    implicit val monoid = immutableMapMonoid[String, Double](doubleMonoid)
    def calc1 = WriterT(Future.successful(Map("doing calc" -> 1.0) -> 11))
    def calc2 = WriterT(Future.successful(Map("doing other" -> 2.0) -> 22))

    for {
      a <- calc1
      b <- calc2
    } yield {
      a + b
    }
  }

  trait Test {
    def f(): Unit = g()
    def g(): Unit = f()
  }

  object ObjectTest extends Test {
    override def f() = println("F")
  }

  def main(args: Array[String]): Unit = {
    println(test())
    println(Await.result(test1().run, Duration.Inf))
    ObjectTest.f()
    ObjectTest.g()
  }

}
