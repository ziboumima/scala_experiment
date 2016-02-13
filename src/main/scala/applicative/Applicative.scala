package applicative
import scala.language.higherKinds
import monad.Functor

/**
  * Created by hibou on 13/02/16.
  */

trait Applicative[F[_]] extends Functor[F] {
  def map2[A, B, C](fa: F[A], fb:F[B])(f: (A, B) => C): F[C]

  def apply[A, B](fab: F[A => B])(fa: F[A]): F[B] = {
    map2(fab, fa)((ab, a) => ab(a))
  }

  // Exercice 12.2

  def mapByApply[A, B](fa: F[A])(f: A => B): F[B] = {
    apply[A, B](unit(f))(fa)
  }

  def map2ByApply[A, B, C](fa: F[A], fb:F[B])(f: (A, B) => C): F[C] = {
    apply[B, C](map(fa)(f.curried))(fb)
  }

  def unit[A](a: => A): F[A]

  def map[A, B](fa: F[A])(f: A => B): F[B] = map2(fa, unit(()))((a, _) => f(a))

  def traverse[A,B](as: List[A])(f: A => F[B]): F[List[B]] =
    as.foldRight(unit(List[B]()))((a, fbs) => map2(f(a), fbs)(_ :: _))

  // Exercice 12.1
  def sequence[A](fas: List[F[A]]): F[List[A]] = {
    fas.foldLeft(unit(List[A]())) { case (fl, fa) =>
      map2(fl, fa)((l, a) => a :: l)
    }
  }

  def replicateM[A](n: Int, fa: F[A]): F[List[A]] = {
    map(fa)(List.fill(n)(_))
  }

  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = {
    map2(fa, fb)((a, b) => (a, b))
  }

  // Exercice 12.3
  def map3[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = {
    ???
  }
  }

object Applicative {


}
