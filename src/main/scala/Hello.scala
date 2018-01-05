import scala.language.higherKinds
import scalaz.{Foldable, Monoid, Validation, ValidationNel}
import scalaz.syntax.foldable._
import scalaz.std.list._
import scalaz.std.vector._
import scalaz.syntax.validation._
import scalaz.syntax.applicative._

object Hello {

  def validate[F[_] : Foldable, A, B : Monoid](in : F[A])(out : A => B): ValidationNel[Throwable, B] = {
    in foldMap (a => Validation.fromTryCatchNonFatal[B](out(a)).toValidationNel)
  }

  def toInts(maybeInts : List[String]): ValidationNel[Throwable, List[Int]] =
    validate(maybeInts)(_.toInt :: Nil)

  def main(args: Array[String]): Unit = {
    println(validate(Vector("1"))(x => Vector(x.toInt)))
    println(validate(Vector("1", "2"))(x => Vector(x.toInt)))
    println(validate(Vector("a", "2"))(x => Vector(x.toInt)))
    println("event 1 ok".successNel[String])
    println(("event 1 ok".successNel[String] |@| "event 2 failed!".failureNel[String] |@| "event 3 failed!".failureNel[String]) {_ + _ + _})
  }
}
