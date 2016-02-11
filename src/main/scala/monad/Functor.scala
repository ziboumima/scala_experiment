package monad

/**
  * Created by hibou on 08/02/16.
  */
trait Functor[F[_]]{
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def distribute[A, B](fab: F[(A, B)]): (F[A], F[B]) = (map(fab)(_._1), map(fab)(_._2))
  def codistribute[A, B](e: Either[F[A], F[B]]): F[Either[A, B]] =
    e match {
      case Left(fa) => map(fa)(Left(_))
      case Right(fb) => map(fb)(Right(_))
    }
}

trait Mon[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
  flatMap(fa)(a  => map(fb)(b => f(a, b)))
}


trait Monad[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  def map[A, B](ma: F[A])(f: A => B): F[B] = flatMap(ma)(a => unit(f(a)))
  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
    flatMap(fa)(a  => map(fb)(b => f(a, b)))
  // Exercice 11.3
  def sequence[A](lma: List[F[A]]): F[List[A]] = {
    lma.foldRight(unit(List[A]()))((ma, mla) => map2(ma, mla)(_ :: _))

  }
  def traverse[A, B](la: List[A])(f: A => F[B]): F[List[B]] = {
    sequence(la.map(f))
  }

  // Exercice 11.4
  def replicateM[A](n: Int, ma: F[A]): F[List[A]] = {
    map(ma)(List.fill(n)(_))
  }
  // Exercice 11.7

  def compose[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] = {
    a => flatMap(f(a))(g)
  }

  // Exercice 11.12: implement join in terme of flatMap
  def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)

  // Exercice 11.13: Flatmap in terms of join and map
  def flatMap2[A, B](fa: F[A])(f: A => F[B]): F[B] = {
    join(map(fa)(f))
  }

  def compose2[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] = {
    a => join(map(f(a))(g))
  }

}


// Exercice 11.17

case class Id[A](value: A){
  def map[B](f: A => B): Id[B] = Id(f(value))
  def flatMap[B](f: A => Id[B]): Id[B] = f(value)
}

object MonadId extends Monad[Id] {
  override def unit[A](a: => A): Id[A] = Id(a)

  override def flatMap[A, B](fa: Id[A])(f: (A) => Id[B]): Id[B] = fa.flatMap(f)
}

object Monad {
  val listFunctor = new Functor[List] {
    def map[A, B](as: List[A])(f: A => B): List[B] = as map f
  }
}
