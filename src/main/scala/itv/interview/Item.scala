package itv.interview

sealed trait Item{
  val unitPrice: Price
}

case object A extends Item{
  override val unitPrice: Price = Price.of(50)
}
case object B extends Item{
  override val unitPrice: Price = Price.of(30)
}
case object C extends Item{
  override val unitPrice: Price = Price.of(20)
}
case object D extends Item{
  override val unitPrice: Price = Price.of(15)
}