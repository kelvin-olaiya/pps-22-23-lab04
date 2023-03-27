package u04lab.code

import org.junit.Assert.assertEquals
import org.junit.Test

class TestSecondDegreePolynomial:
  private val simplePolynomial = SecondDegreePolynomial(1.0, 0, 3)
  private val anotherPolynomial = SecondDegreePolynomial(0.0, 1, 0.0)
  private val fullPolynomial = SecondDegreePolynomial(3.0, 2.0, 5.0)

  @Test def testEquals(): Unit =
    val sum = simplePolynomial + anotherPolynomial
    assertEquals(SecondDegreePolynomial(1.0, 1.0, 3.0), sum)

  @Test def testMultipleOperations(): Unit =
    val multipleOperations = fullPolynomial - (anotherPolynomial + simplePolynomial)
    assertEquals(SecondDegreePolynomial(4.0, 3.0, 8.0), multipleOperations)
