package shaplesstest

import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy, the}

case class Employee(name: String, number: Int, manager: Boolean)
case class IceCream(name: String, numCherries: Int, inCone: Boolean)

sealed trait Shape

final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

sealed trait Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
case class Leaf[A](value: A) extends Tree[A]



trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc

  def createEncoder[A](func: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      def encode(value: A): List[String] = func(value)
    }

  implicit val stringEncoder: CsvEncoder[String] = createEncoder(str => List(str))

  implicit val intEncoder: CsvEncoder[Int] =
    createEncoder(num => List(num.toString))

  implicit val boolEncoder: CsvEncoder[Boolean] =
    createEncoder(bool => List(if(bool) "yes" else "no"))

  implicit val doubleEncoder: CsvEncoder[Double] =
    createEncoder(d => List(d.toString))

  implicit val hnilEncoder: CsvEncoder[HNil] = createEncoder(hnil => Nil)


  implicit def hlistEncoder[H, T <: HList](implicit
                                          hEncoder: Lazy[CsvEncoder[H]],
                                           tEncoder: CsvEncoder[T]
                                          ): CsvEncoder[H :: T] = createEncoder {
    case h :: t =>
      hEncoder.value.encode(h) ++ tEncoder.encode(t)
  }


  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], rEncoder: Lazy[CsvEncoder[R]]): CsvEncoder[A] =
    createEncoder(a => rEncoder.value.encode(gen.to(a)))


  implicit val cnilEncoder: CsvEncoder[CNil] = createEncoder(cnil => throw new Exception("Impossible!"))

  implicit def coproductEncoder[H, T <: Coproduct](implicit hEncoder: Lazy[CsvEncoder[H]], tEncoder: CsvEncoder[T]): CsvEncoder[H :+: T] =
    createEncoder {
      case Inl(h) => hEncoder.value.encode(h)
      case Inr(r) => tEncoder.encode(r)
    }

}


object AutomaticDerivation {

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values.map(value => enc.encode(value).mkString(",")).mkString("\n")

  def main(args: Array[String]): Unit = {

    val employees = List(
      Employee("Bill", 1, manager = true),
      Employee("Peter", 2, manager = false),
      Employee("Milton", 3, manager = false)
    )

    val iceCreams = List(
      Ice("Bill", 1, inCone = true),
      Ice("Peter", 2, inCone = false),
      Ice("Milton", 3, inCone = false)
    )

    val shapes = List(
      Rectangle(3.0, 4.0),
      Circle(1.0)
    )

    println(writeCsv(employees))
    println(writeCsv(iceCreams))
    writeCsv[Shape](shapes)

    implicitly[CsvEncoder[Tree[Int]]]
    CsvEncoder[Tree[Int]]

  }
}
