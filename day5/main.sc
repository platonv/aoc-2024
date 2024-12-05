#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

val filename = args(0)

val inputString = readFile(filename)

println(inputString.split("\n").toList.map(_.isEmpty))

val (rulesLines, updateLines) = inputString.split("\n").toList.filterNot(_.isEmpty).partition(_.contains("|"))

val rules = rulesLines.foldLeft(Map.empty[Int, List[Int]]) { (acc, line) =>
    line.split("\\|").toList match {
        case List(rule, before) => 
            val x = rule.trim.toInt
            val y = before.trim.toInt
            acc.updated(x, y :: acc.getOrElse(x, List.empty))
    }
}

val invertedRules = rulesLines.foldLeft(Map.empty[Int, List[Int]]) { (acc, line) =>
    line.split("\\|").toList match {
        case List(rule, before) => 
            val x = rule.trim.toInt
            val y = before.trim.toInt
            acc.updated(x, y :: acc.getOrElse(x, List.empty))
    }
}

val updates = updateLines.map(_.split(",").map(_.trim.toInt).toList)

def checkRules(updates: List[Int]): Boolean =
    updates.zipWithIndex.forall { case (update, index) =>
        val others = updates.takeRight(updates.size - index - 1)

        others.forall { other => rules.get(other) match {
            case Some(values) => !values.contains(update)
            case None => true
        } }
    }

val res = updates.map { update =>
    if (checkRules(update)) {
        update(update.length / 2)
    } else {
        0
    }
}.sum

println(rules)
println(res)
