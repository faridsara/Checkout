package itv.interview

import org.scalacheck.Arbitrary
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalacheck.Gen

class CheckoutTestsProp extends AnyPropSpec with ScalaCheckPropertyChecks with Matchers  {
  val offers: PartialFunction[Item, Offer] = {
    case A => Offer(A, Quantity.of(3), Price.of(130))
    case B => Offer(B, Quantity.of(2), Price.of(45))
  }

//  val genItemList: Gen[List[Item]] = Gen.listOfN(3, Gen.oneOf(A, B, C, D))
  val genItemList: Gen[List[Item]] = Gen.listOf(Gen.oneOf(A, B, C, D))
  implicit val arb: Arbitrary[List[Item]] = Arbitrary(genItemList)

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 100)

  property("Checkout with offers should at max, return the total sum of the unit prices") {
    forAll(genItemList) { items =>
      whenever(items.nonEmpty) {
        val priceWithoutOffer: Price = Price.of(items.map(_.unitPrice.value).sum)
        val priceWithOffer: Price = Checkout(offers).calculatePriceForListOfItems(items)
        priceWithOffer.value should be <= priceWithoutOffer.value
      }
    }
  }
  property("return the total sum of unit prices if used without offers"){
    forAll(genItemList) { items =>
      whenever(items.nonEmpty) {
        val priceWithoutOffer: Price = Price.of(items.map(_.unitPrice.value).sum)
        val priceWithEmptyOffer: Price = Checkout(PartialFunction.empty).calculatePriceForListOfItems(items)
        priceWithoutOffer should be(priceWithEmptyOffer)
      }
    }
  }
}
