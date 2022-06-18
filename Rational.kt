package rational

import java.util.*
import kotlin.math.abs

class Rational(var numerator: Int, var denominator: Int = 1) {

    private val superNums: List<String> = ArrayList(listOf("⁰", "¹", "²", "³", "⁴", "⁵", "⁶", "⁷", "⁸", "⁹"))
    private val subNums: List<String> = ArrayList(listOf("₀", "₁", "₂", "₃", "₄", "₅", "₆", "₇", "₈", "₉"))

    init {
        if (denominator == 0)
            throw IllegalArgumentException("denominator cannot be 0")
        else if (denominator < 0) {
            numerator = -numerator
            denominator = -denominator
        }
        if (numerator == 0) {
            denominator = 1
        }
        else {
            val divisor = greatestCommonDivisor(numerator, denominator)
            if (divisor > 1) {
                numerator /= divisor
                denominator /= divisor
            }
        }
    }

    private fun toSuper(x: Int): String {
        var string = ""
        for (character: Char in "%d".format(x))
            string += superNums[Integer.parseInt(character.toString())]
        return string
    }

    private fun toSub(x: Int): String {
        var string = ""
        for (character: Char in "%d".format(x))
            string += subNums[Integer.parseInt(character.toString())]
        return string
    }


    override fun toString(): String {
        var output = ""
        if (numerator < 0)
            output += "-"
        if (abs(numerator) / denominator > 0)
            output += "%d".format(abs(numerator)  / denominator)
        if (abs(numerator)  % denominator > 0)
            output += "%s/%s".format(toSuper(abs(numerator)  % denominator), toSub(denominator))
        if (output == "")
            output += "0"
        return output
    }

    private tailrec fun greatestCommonDivisor(a: Int, b: Int): Int {
        var a = a
        var b = b
        a = abs(a)
        b = abs(b)
        if (a == b) return a
        return if (a > b) greatestCommonDivisor(a - b, a) else greatestCommonDivisor(a, b - a)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator
        result = 31 * result + denominator
        return result
    }

    operator fun unaryPlus(): Rational {
        return Rational(numerator, denominator)
    }

    operator fun unaryMinus(): Rational {
        return Rational(-numerator, denominator)
    }

    operator fun inc(): Rational {
        return Rational(numerator + denominator, denominator)
    }

    operator fun dec(): Rational {
        return Rational(numerator - denominator, denominator)
    }

    operator fun plus(that: Rational): Rational {
        return Rational(this.numerator * that.denominator + that.numerator * this.denominator, this.denominator * that.denominator)
    }

    operator fun minus(that: Rational): Rational {
        return Rational(this.numerator * that.denominator - that.numerator * this.denominator, this.denominator * that.denominator)
    }

    operator fun times(that: Rational): Rational {
        return Rational(this.numerator * that.numerator, this.denominator * that.denominator)
    }

    operator fun div(that: Rational): Rational {
        return Rational(this.numerator * that.denominator, this.denominator * that.numerator)
    }
}