package monoid

/**
  * Created by hibou on 28/01/16.
  */
trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

object Toto {
  val stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String) = a1 + a2
    val zero = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    val zero = Nil
  }
  // Exercice 10.01

  val intAddition = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 + a2
    val zero = 0
  }

  val intMultiplication = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 * a2
    val zero = 0
  }

  val booleanAnd = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 && a2
    val zero = true
  }

  val booleanOr = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 || a2
    val zero = true
  }

  // Exercice 10.02
  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def op(a1: Option[A], a2: Option[A]) = a1 orElse a2
    val zero = None
  }
  // Exercice 10.03
  def endMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    def op(a1: (A) => A, a2: (A) => A) = x => a1(a2(x))
    val zero = (x: A) => x
  }

  def testFold() = {
    val words = List("Hic", "Est", "Index")
    val s = words.foldRight(stringMonoid.zero)(stringMonoid.op)
    val t = words.foldLeft(stringMonoid.zero)(stringMonoid.op)
  }

  // Exercice 10.05
  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B = {
    as.map(f).fold(m.zero)(m.op)
  }

  // Exercice 10.07
  def foldMapV[A, B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = {
    val size = v.length
    size match {
      case 0 => m.zero
      case 1 => f(v.head)
      case s => {
        val (first, second) = v.splitAt(size / 2)
        m.op(foldMapV(first, m)(f), foldMapV(second, m)(f))
      }
    }
  }

  def testFoldMap() = {
    val result = foldMapV(Vector(1, 2, 3, 4), stringMonoid)(_.toString)
    println(result)
  }

  sealed trait WC
  case class Stub(chars: String) extends WC
  case class Part(lStub: String, words: Int, rStub: String) extends WC
  val wcMonoid: Monoid[WC] = new Monoid[WC]{
    def op(a1: WC, a2: WC) = {
      case
    }
  }



}
