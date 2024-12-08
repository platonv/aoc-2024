#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

val filename = args(0)

val inputString = readFile(filename)

val inputMatrix = inputString.split("\n").map(_.toCharArray)

val antennas = inputMatrix.zipWithIndex.flatMap { case (row, i) =>
    row.zipWithIndex.filterNot(_._1 == '.').map { case (freq, j) =>
        freq -> (i, j)
    }
}

val antennasMap = antennas.groupBy(_._1).mapValues(_.map(_._2).toList).toMap

def findAntiNodes(x: (Int, Int), y: (Int, Int), maxI: Int, maxJ: Int): List[(Int, Int)] = {
    val z = (2 * x._1 - y._1, 2 * x._2 - y._2)
    // symmetric point of y with respect to x
    val z2 = (2 * y._1 - x._1, 2 * y._2 - x._2)

    List(z, z2)
}

def isInLine(x: (Int, Int), y: (Int, Int), z1: Int, z2: Int): Boolean = {
    val (x1, x2) = x
    val (y1, y2) = y
    x1 * (y2 - z2) + y1 * (z2 - x2) + z1 * (x2 - y2) == 0
}

// part1
// val antiNodes = antennasMap.mapValues { case antennas =>
//     antennas.combinations(2).flatMap { case List(x, y) =>
//         findAntiNodes(x, y, inputMatrix.length, inputMatrix(0).length).filter { case (i, j) =>
//             i >= 0 && i < inputMatrix.length && j >= 0 && j < inputMatrix(0).length
//         }.toList
//     }
// }.map(_._2).flatten.toSet.toList

// inputMatrix.zipWithIndex.foreach { (row, i) =>
//     row.zipWithIndex.foreach { (freq, j) =>
//         if (antiNodes.contains((i, j))) {
//             print('#')
//         } else if (antennasMap.getOrElse(freq, List.empty).contains((i, j))) {
//             print(freq)
//         } else {
//             print('.')
//         }
//     }
//     println()
// }

// println(antiNodes.size)

val inlineNodes = antennasMap.mapValues { case antennas =>
    antennas.combinations(2).flatMap { case List(x, y) =>
        for {
            i <- 0 until inputMatrix.length
            j <- 0 until inputMatrix(0).length
            if isInLine(x, y, i, j)
        } yield (i, j)
    }
}.map(_._2).flatten.toSet.toList

inputMatrix.zipWithIndex.foreach { (row, i) =>
    row.zipWithIndex.foreach { (freq, j) =>
        if (inlineNodes.contains((i, j))) {
            print('#')
        } else if (antennasMap.getOrElse(freq, List.empty).contains((i, j))) {
            print(freq)
        } else {
            print('.')
        }
    }
    println()
}

println(inlineNodes.size)