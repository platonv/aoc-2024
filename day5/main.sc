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

def swap(arr: List[Int], a: Int, b: Int): List[Int] = 
    arr.updated(a, arr(b)).updated(b, arr(a))

def tryFix(updates: List[Int]): List[Int] =
    val res = updates.zipWithIndex.flatMap { case (update, index) =>
        val others = updates.takeRight(updates.size - index - 1)

        others.flatMap { other => rules.get(other) match {
            case Some(values) => if (values.contains(update)) {
                Some((update, other))
            } else None
            case None => None
        } }
        
    }
    val fixed = res.foldLeft(updates) { case (acc, (update, other)) =>
        val updateIdx = acc.indexOf(update)
        val otherIdx = acc.indexOf(other)
        swap(acc, updateIdx, otherIdx)
    }
    if (checkRules(fixed)) {
        fixed
    } else {
        tryFix(fixed)
    }

val part1 = updates.map { update =>
    if (checkRules(update)) {
        update(update.length / 2)
    } else {
        0
    }
}.sum

println("Part 1:")
println(part1)

println(rules)

val part2 = updates.map { update =>
    if (!checkRules(update)) {
        val fixed = tryFix(update)
        println(s"Original: $update")
        println(s"Fixed: $fixed")
        fixed(fixed.length / 2)
    } else {
        0
    }
}.sum

println("Part 2:")
println(part2)
