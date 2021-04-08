package itv.interview

import scala.collection.MapView

trait Checkout {
  def calculatePriceForListOfItems (list : List[Item]): Price
}

object Checkout {

  def apply(offers: PartialFunction[Item, Offer]): Checkout = new Checkout {
    def calculateOfferPrice(quantity: Quantity): Offer => Price = offer => {
      val numberOfTimesOfferToBeApplied= quantity.value/ offer.quantity.value
      val numberOfFullPriceItems =quantity.value % offer.quantity.value
      Price.of(
        (numberOfTimesOfferToBeApplied * offer.offerPrice.value) +
        (numberOfFullPriceItems * offer.item.unitPrice.value)
      )
    }

    def calculatePriceForItems(item: Item, quantity: Quantity): Price = {
      (offers andThen calculateOfferPrice(quantity))
        .applyOrElse[Item, Price](item, default = i => Price.of(i.unitPrice.value * quantity.value))
    }

    def countItems(list : List[Item]): MapView[Item, Quantity] = list.groupBy(identity).view.mapValues(occurrence => Quantity.of(occurrence.size))

    def calculatePriceForListOfItems (list : List[Item]): Price = {
      val result = countItems(list).map{
        case (item,quantity)=>
          calculatePriceForItems(item,quantity)
      }.map(_.value).sum

      Price.of(result)
    }

  }

}