/**
  * Created by hibou on 14/01/16.
  */
class Outer {
  trait Inner
  def y = new Inner {}
  def foo(x: this.Inner) = null
  def bar(x: Outer#Inner) = null


}
