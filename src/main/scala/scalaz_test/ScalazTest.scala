package scalaz_test

import scalaz.{ValidationNel, Validation}
import scalaz._
import Scalaz._
/**
  * Created by nnguyen on 3/1/16.
  */
object ScalazTest {
  def toInts(maybeInts : List[String]): ValidationNel[Throwable, List[Int]] = {
    val validationList = maybeInts map { s =>
      Validation.fromTryCatchNonFatal(s.toInt :: Nil).toValidationNel
    }
    validationList reduce (_ +++ _)
  }

  def validate[F[_] : Foldable, A, B : Monoid]
  (in : F[A])
  (out : A => B): ValidationNel[Throwable, B] = {
    in foldMap (a => Validation.fromTryCatchNonFatal[B](out(a)).toValidationNel)
  }

  def main(args: Array[String]): Unit = {
    print(toInts(List("1", "23", "4")))
  }


}
