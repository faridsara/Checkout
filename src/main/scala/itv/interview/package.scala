package itv

package object interview {

  case class Price (value: Int) extends AnyVal
  object Price{
    def of(value: Int): Price = Price(value)
  }

  case class Quantity (value: Int) extends AnyVal
  object Quantity{
    def of(value: Int): Quantity = Quantity(value)
  }

}
