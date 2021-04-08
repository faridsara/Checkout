import itv.interview
import itv.interview.{A, B, C, Checkout, D, Item, Offer, Price, Quantity}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import matchers._

class CheckOutTests extends AnyFlatSpec with should.Matchers {
  val offers: PartialFunction[Item, Offer] = {
    case A => interview.Offer(A, Quantity.of(3),Price.of(130))
    case B => interview.Offer(B, Quantity.of(2), Price.of(45))
  }

  "Checkout" should "be able to calculate the price for a list of items" in {
    Checkout(offers).calculatePriceForListOfItems(List(A,B,C,D)).value should be (115)
  }

  "Checkout" should "be able to calculate the offer price for a list of items" in {
    Checkout(offers).calculatePriceForListOfItems(List(A,A,A,B,B)).value should be (175)
  }

  "Checkout" should "be able to calculate the offer price for a list of items regardless of input order" in {
    Checkout(offers).calculatePriceForListOfItems(List(A,B,A,B,A)).value should be (175)
  }

  "Checkout" should "return 0 price if empty list given" in {
    Checkout(offers).calculatePriceForListOfItems(List()).value should be (0)
  }

  //todo
  //can be turned into property based testing
  "Checkout with offers" should "at max, return the total sum of the unit prices" in {
    val items = List(A,B,A,B,A)
    val priceWithoutOffer: Price = Price.of(items.map(_.unitPrice.value).sum)
    val priceWithOffer: Price = Checkout(offers).calculatePriceForListOfItems(items)

    priceWithOffer.value should be <= priceWithoutOffer.value
  }

  //todo
  //can be turned into property based testing
  "Checkout" should "return the total sum of unit prices if used without offers" in {
    val items = List(A,B,A,B,A)
    val priceWithoutOffer: Price = Price.of(items.map(_.unitPrice.value).sum)
    val priceWithEmptyOffer: Price = Checkout(PartialFunction.empty).calculatePriceForListOfItems(items)

    priceWithoutOffer should be (priceWithEmptyOffer)
  }

}
