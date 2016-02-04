package foldable
import scala.language.higherKinds
import monoid.Monoid
import monoid.Toto

/**
  * Created by hibou on 02/02/16.
  */

trait Foldable[F[_]] {
  def foldRight[A, B](as: F[A])(z: B)(f: (A, B) => B): B
  def foldLeft[A, B](as: F[A])(z: B)(f: (B, A) => B): B
  def foldMap[A, B](as: F[A])(f: A => B)(mb: Monoid[B]): B
  def concatenate[A](as: F[A])(m: Monoid[A]): A = foldLeft(as)(m.zero)(m.op)
  def toList[A](fa: F[A]): List[A] = {

    ???
  }
}

object ListFoldable extends Foldable[List]{
  override def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B = as.foldRight(z)(f)

  override def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B): B = as.foldLeft(z)(f)

  override def foldMap[A, B](as: List[A])(f: (A) => B)(mb: Monoid[B]): B =
    as.map(f).fold(mb.zero)(mb.op)
}


object IndexedSeqFoldable extends Foldable[IndexedSeq]{
  override def foldRight[A, B](as: IndexedSeq[A])(z: B)(f: (A, B) => B): B =
    as.foldRight(z)(f)

  override def foldLeft[A, B](as: IndexedSeq[A])(z: B)(f: (B, A) => B): B =
    as.foldLeft(z)(f)

  override def foldMap[A, B](as: IndexedSeq[A])(f: (A) => B)(mb: Monoid[B]): B =
    as.map(f).fold(mb.zero)(mb.op)
}


object StreamFoldable extends Foldable[Stream] {
  override def foldRight[A, B](as: Stream[A])(z: B)(f: (A, B) => B): B =
  as.foldRight(z)(f)

  override def foldLeft[A, B](as: Stream[A])(z: B)(f: (B, A) => B): B =
    as.foldLeft(z)(f)

  override def foldMap[A, B](as: Stream[A])(f: (A) => B)(mb: Monoid[B]): B =
    as.map(f).fold(mb.zero)(mb.op)
}

// Exercice 10.13

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object TreeFoldable extends Foldable[Tree] {

  override def foldLeft[A, B](as: Tree[A])(z: B)(f: (B, A) => B): B = as match {
    case Leaf(a) => f(z, a)
    case Branch(l, r) => foldLeft(r)(foldLeft(l)(z)(f))(f)
  }

  override def foldRight[A, B](as: Tree[A])(z: B)(f: (A, B) => B): B = as match {
    case Leaf(a) => f(a, z)
    case Branch(l, r) => foldRight(l)(foldRight(r)(z)(f))(f)
  }

  override def foldMap[A, B](as: Tree[A])(f: (A) => B)(mb: Monoid[B]): B = as match {
    case Leaf(a) => f(a)
    case Branch(l, r) => mb.op(foldMap(l)(f)(mb), foldMap(r)(f)(mb))
  }
}


// Exercice 10.14

object OptionFoldable extends Foldable[Option] {
  override def foldRight[A, B](as: Option[A])(z: B)(f: (A, B) => B): B = as match {
    case Some(a) => f(a, z)
    case None => z
  }

  override def foldLeft[A, B](as: Option[A])(z: B)(f: (B, A) => B): B = as match {
    case Some(a) => f(z, a)
    case None => z
  }

  override def foldMap[A, B](as: Option[A])(f: (A) => B)(mb: Monoid[B]): B = as match {
    case Some(a) => f(a)
    case None => mb.zero

  }
}

object ComposingMonoids {
  def productMonoid[A, B](A: Monoid[A], B: Monoid[B]): Monoid[(A, B)] = {
    new Monoid[(A, B)] {
      def op(x: (A,B), y: (A,B) ) = (A.op(x._1, y._1) , B.op(x._2, y._2))
      val zero = (A.zero, B.zero)
    }
  }

  def mapMergeMonoid[K, V](v: Monoid[V]): Monoid[Map[K, V]] = {
    new Monoid[Map[K, V]] {
      override def op(a1: Map[K, V], a2: Map[K, V]): Map[K, V] = {
        (a1.keySet ++ a2.keySet).foldLeft(zero){(acc, k) =>
            acc.updated(k, v.op(a1.getOrElse(k, v.zero), a2.getOrElse(k, v.zero)))
        }
      }
      override def zero: Map[K, V] = Map.empty
    }
  }

  // Exercice 10.17
  def functionMonoid[A, B](b: Monoid[B]): Monoid[A => B] = {
    new Monoid[A => B] {
      override def op(a1: (A) => B, a2: (A) => B): (A) => B = {
        def result(x: A): B = {
          val x1 = a1(x)
          val x2 = a2(x)
          b.op(x1, x2)
        }
        result
      }

      override def zero: (A) => B = {
        x => b.zero
      }
    }
  }

  // Exercice 10.18
  def bag[A](as: IndexedSeq[A]): Map[A, Int] = {
    val m: Monoid[Map[A, Int]] = mapMergeMonoid(Toto.intAddition)
    IndexedSeqFoldable.foldMap(as)(x => Map[A, Int](x -> 1))(m)
  }
}

