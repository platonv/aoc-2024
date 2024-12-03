#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

val filename = args(0)

val inputString = readFile(filename)

val pattern = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)|do\\(\\)|don't\\(\\)".r

val matches = pattern.findAllMatchIn(inputString).toList

val part1Res = matches.map { m =>
    m.group(1).toInt * m.group(2).toInt
}.sum

println("Part 1")
println(part1Res)

val res =matches.foldLeft((true, 0)) {
    case ((true, result), m) => 
        m.matched match {
            case "do()" => (true, result)
            case "don't()" => (false, result)
            case _ => 
                val number = m.group(1).toInt * m.group(2).toInt
                (true, result + number)
        }
    case ((false, result), m) => 
        m.matched match {
            case "do()" => (true, result)
            case "don't()" => (false, result)
            case _ => (false, result)
        }
}

println("Part 2")
println(res)