#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

val filename = args(0)

val inputString = readFile(filename)

val XMAS_STR = "XMAS"

object Directions:
    val N = (0, -1)
    val S = (0, 1)
    val E = (1, 0)
    val W = (-1, 0)
    val NE = (-1, 1)
    val NW = (-1, -1)
    val SE = (1, 1)
    val SW = (1, -1)

    val all = List(N, S, E, W, NE, NW, SE, SW)

val inputMatrix = inputString.split("\n").map(_.toCharArray).toArray

def bfsXMAS(currentPos: (Int, Int), direction: (Int, Int), index: Int): Int =
    val (ni, nj) = (currentPos._1 + direction._1, currentPos._2 + direction._2)
    // println(s"ni: $ni, nj: $nj, index: ${XMAS_STR(index)}")
    if (ni < 0 || nj < 0 || ni >= inputMatrix.length || nj >= inputMatrix(ni).length) then
        0
    else if (inputMatrix(ni)(nj) == XMAS_STR(index + 1)) then
        if (index + 1 == XMAS_STR.length - 1) then
            1
        else
            bfsXMAS((ni, nj), direction, index + 1)
    else
        0

def findXMASInMatrix =
    val res = for {
        i <- 0 until inputMatrix.length
        j <- 0 until inputMatrix(i).length
        if inputMatrix(i)(j) == XMAS_STR(0)
    } yield {
        Directions.all.map(d => bfsXMAS((i, j), d, 0)).sum
    }
    res

def findXXMASInMatrix =
    def checkNESW(i: Int, j: Int) = 
        (inputMatrix(i + Directions.NE._1)(j + Directions.NE._2) == 'M' &&
        inputMatrix(i + Directions.SW._1)(j + Directions.SW._2) == 'S') ||
        (inputMatrix(i + Directions.NE._1)(j + Directions.NE._2) == 'S' &&
        inputMatrix(i + Directions.SW._1)(j + Directions.SW._2) == 'M')

    def checkNWSE(i: Int, j: Int) = 
        (inputMatrix(i + Directions.NW._1)(j + Directions.NW._2) == 'M' &&
        inputMatrix(i + Directions.SE._1)(j + Directions.SE._2) == 'S') ||
        (inputMatrix(i + Directions.NW._1)(j + Directions.NW._2) == 'S' &&
        inputMatrix(i + Directions.SE._1)(j + Directions.SE._2) == 'M')

    val res = for {
        i <- 1 until inputMatrix.length - 1
        j <- 1 until inputMatrix(i).length - 1
        if inputMatrix(i)(j) == 'A'
    } yield {
        println(s"i: $i, j: $j")
        if (checkNESW(i, j) && checkNWSE(i, j)) then
            1
        else
            0
    }
    res.sum

println("Part1:")
println(findXMASInMatrix.sum)
println("Part2:")
println(findXXMASInMatrix)
