package parallelism

import com.sun.org.apache.bcel.internal.generic.LSTORE

/**
  * Created by hibou on 30/01/16.
  */

trait Par[A]

object Par {
  def unit [A](a: => A): Par[A] = ???
  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))
  def get[A](a: Par[A]): A = ???
  def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] = ???
  def fork[A](a: => Par[A]): Par[A] = ???
  def run[A](a: Par[A]): A = ???
}

object Parallel {
  def sum(ints: IndexedSeq[Int]): Int = {
    if (ints.size <= 1)
      ints.headOption getOrElse 0
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      sum(l) + sum(r)
    }
  }

  def sumPar(ints: IndexedSeq[Int]): Int = {
    if (ints.size <= 1)
      ints.headOption getOrElse 0
    else {
      val (l, r) = ints.splitAt(ints.length / 2)
      val s = Par.map2(Par.fork(Par.unit(sum(l))), Par.fork(Par.unit(sum(r))))(_ + _)
      Par.get(s)
    }
  }
}

