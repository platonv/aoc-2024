#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

def removeIndex[A](s: Seq[A], n: Int): Seq[A] = s.indices.collect { case i if i != n => s(i) }

final case class Report(levels: Seq[Int]):
    def valid(levels: Seq[Int] = levels): Boolean =
        val increasing = levels.sliding(2).forall {
            case Seq(a, b) => a < b
            case _ => false
        }
        val decreasing = levels.sliding(2).forall {
            case Seq(a, b) => a > b
            case _ => false
        }

        if (increasing || decreasing)
            levels.sliding(2).forall {
                case Seq(a, b) => if (Math.abs(b - a) > 0 && Math.abs(b - a) <= 3) true else 
                    false
                case _ => 
                    false
            }
        else {
            false
        }

    def dampenedValid: Boolean =
        println(s"Checking $levels")
        List.range(0, levels.length).map { i =>
            val result = valid(removeIndex(levels, i))
            println(removeIndex(levels, i))
            if (result) {
                println(s"$levels without ${levels(i)} is VALID")
                true
            } else {
                println(s"$levels without ${levels(i)} is INVALID")
                false
            }
        }.count(identity) >= 1

object Report:
    def fromString(str: String): Either[Error, Report] =
        val values = str.split(" ").map(_.toIntOption).map {
            case Some(value) => Right(value)
            case None => Left("Invalid number")
        }.toList

        values.find(_.isLeft) match
            case Some(Left(error)) => Left(error)
            case _ => Right(Report(values.map(_.getOrElse(0))))

val filename = args(0)

println(readFile(filename))

val lines = readFile(filename).split("\n")

val validReports = lines.map {line =>
    Report.fromString(line).map(_.valid)
}

val validDampened = lines.map {line =>
    for {
        report <- Report.fromString(line)
    } yield {
        if (report.dampenedValid)
            println("Valid")
        else
            println("Invalid")
        report.dampenedValid
    }
}

println("Result Part 2:")
val dampenedValidCount = validDampened.map(_.contains(true)).count(identity)
println(dampenedValidCount)
