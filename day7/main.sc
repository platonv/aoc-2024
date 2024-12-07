#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

val filename = args(0)

val inputString = readFile(filename)

// Generate all combinations of n operators
def generateOperators(n: BigInt): List[List[String]] =
    val possibleOperators = List("+", "*", "||")
    if n == 0 then List(List())
    else
        possibleOperators.map { operator =>
            generateOperators(n - 1).map { operators =>
                operator :: operators
            }
        }.flatten


final case class Equation(result: BigInt, numbers: List[BigInt]) {
    def isCorrect(operators: List[String]) = 
        val res = numbers.tail.zip(operators).foldLeft(numbers.head) { case (acc, (num, op)) =>
            op match
                case "+" => acc + num
                case "*" => acc * num
                case "||" => BigInt(acc.toString + num.toString)
        }
        res == this.result

    def canBeCorrect = generateOperators(numbers.length - 1).exists(isCorrect)
}

object Equation:
    def fromString(s: String): Either[Error, Equation] =
        s.split(":").toList match
            case result :: numbers :: Nil =>
                Right(Equation(BigInt(result), numbers.split(" ").toList.filterNot(_.isEmpty()).map(BigInt(_))))
            case _ => Left("Invalid input")

val inputLines = inputString.split("\n").toList

val res = inputLines.map { lineStr =>
    Equation.fromString(lineStr) match
        case Right(equation) =>
            println(s"Solving $equation")
            println(equation)
            println(equation.canBeCorrect)
            if (equation.canBeCorrect) equation.result else BigInt(0)
        case Left(error) => BigInt(0)
    }

println(res)

println("Result")
println(res.sum)
