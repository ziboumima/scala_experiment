package scalaz_test.statet
import scalaz._

/**
  * Created by nnguyen on 3/1/16.
  */
object StateT {
  type Stack = List[Int]

  val pop = State[Stack, Int] {
    case x :: xs => (xs, x)
  }
  def push(a: Int) = State[Stack, Unit] {
    case xs => (a :: xs, ())
  }


  def stackManip: State[Stack, Int] = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield b

  def main(args: Array[String]): Unit = {
    print(stackManip(List(5, 8, 2, 1)))
  }

}
