package rational

import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList

/**
 * A client of the Rational class / ADT
 * @author Nick Patrikeos
 */
object FunFractions {
    private val OPERATORS = arrayOf("+", "-", "×", "÷")
    private val NUMERICS = ArrayList(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"))
    private fun randomRational(): Rational {
        val numerator = (ThreadLocalRandom.current().nextInt(1, 11))
        var denominator = (ThreadLocalRandom.current().nextInt(-10, 10))
        if (denominator == 0)
            denominator = 10
        return Rational(numerator, denominator)
    }

    private fun play() {
        val scanner = Scanner(System.`in`)
        var userAnswer = "0"
        while (userAnswer != "") {
            val num1 = randomRational()
            val num2 = randomRational()
            val operator = OPERATORS[(Math.random() * 4).toInt()]
            println("\nWhat is %s %s %s?%n".format(num1, operator, num2))
            val answer: Rational = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "×" -> num1 * num2
                "÷" -> num1 / num2
                else -> throw IllegalArgumentException("operator must be operator")
            }
            val answersSet = hashSetOf(answer)
            while (answersSet.size < 4)
                answersSet.add(randomRational())
            val answers = answersSet.toList().shuffled()
            for ((index, element) in answers.withIndex())
                println("%s) %s".format(index, element))
            print("> ")
            userAnswer = scanner.nextLine()
            if (userAnswer != "" && isNumeric(userAnswer) && userAnswer.toInt() < 4) {
                if (answers[userAnswer.toInt()] == answer)
                    println("Correct!")
                else
                    println("Incorrect. The correct answer was: %s%n".format(answer))
            } else
                println("Invalid input. The correct answer was: %s%n".format(answer))
        }
        scanner.close()
    }

    private fun isNumeric(answer: String): Boolean {
        return NUMERICS.contains(answer)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        play()
    }
}