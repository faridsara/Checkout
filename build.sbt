name := "itv.interview.Checkout"

version := "0.1"

scalaVersion := "2.13.5"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.7" % s"$Test,$IntegrationTest",
//  "org.scalacheck" %% "scalacheck" % "1.15.3" % "test",
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.7.0" % "test"
)
