package shaplesstest
import shapeless.{:+:, CNil, Generic, HNil, Inl, Inr}

case class Ice(name: String, numCherries: Int, inCone: Boolean)

case class OtherIceCream(x: String, y: Int, z: Boolean)

object Main {
  def generic(): Unit = {
    val product = "Sunday" :: 1 :: false :: HNil
    val first = product.head
    val second = product.tail.head
    val rest = product.tail.tail.head

    val iceCreamGen = Generic[Ice]
    val iceCream = Ice("toto", 1, true)
    val repl = iceCreamGen.to(iceCream)
    println(repl)
    val iceCream2 = iceCreamGen.from(repl)
    println(iceCream2)

    val another = Generic[OtherIceCream].from(Generic[Ice].to(iceCream))
    println(another)

  }

  def coProduct(): Unit = {
    case class Red()
    case class Amber()
    case class Green()

    type Light = Red :+: Amber :+: Green :+: CNil

    val red: Light = Inl(Red())
    val green: Light = Inr(Inr(Inl(Green())))
  }




  def main(args: Array[String]): Unit = {
    generic()
  }
}
