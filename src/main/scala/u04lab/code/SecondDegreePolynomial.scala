package u04lab.code

import scala.quoted.ToExpr.DoubleToExpr

// Express a second degree polynomial
// Structure: secondDegree * X^2 + firstDegree * X + constant
trait SecondDegreePolynomial:
  def constant: Double
  def firstDegree: Double
  def secondDegree: Double
  def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial
  def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial

object SecondDegreePolynomial:
  def apply(secondDegree: Double, firstDegree: Double, constant: Double): SecondDegreePolynomial =
    SecondDegreePolynomialImpl(secondDegree, firstDegree, constant)

  def unapply(polynomial: SecondDegreePolynomial): scala.Option[(Double, Double, Double)] =
    scala.Some((polynomial.secondDegree, polynomial.firstDegree, polynomial.constant))

  private case class SecondDegreePolynomialImpl(
    secondDegree: Double,
    firstDegree: Double,
    constant: Double
  ) extends SecondDegreePolynomial:

    override def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial = polynomial match
      case SecondDegreePolynomial(s, f, c) => SecondDegreePolynomial(s + secondDegree, f + firstDegree, c + constant)

    override def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial = polynomial match
      case SecondDegreePolynomial(s, f, c) => SecondDegreePolynomial(secondDegree - s, firstDegree - f, constant - c)
