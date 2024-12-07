#!/usr/bin/env -S scala-cli

type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

val filename = args(0)

val inputString = readFile(filename)

val inputMatrix = inputString.split("\n").map(_.toCharArray()).toArray

def printMatrix(matrix: Array[Array[Char]]): Unit = {
    for (row <- matrix) {
        println(row.mkString)
    }
}

val startingPositionI = inputMatrix.indexWhere(_.contains('^'))
val startingPositionJ = inputMatrix(startingPositionI).indexOf('^')

def turnRightDirection(position: (Int, Int), direction: (Int, Int)): (Int, Int) = {
    def justRight(d: (Int, Int)) = d match {
        case (0, 1) => (1, 0)
        case (1, 0) => (0, -1)
        case (0, -1) => (-1, 0)
        case (-1, 0) => (0, 1)
    }
    inputMatrix(position._1 + justRight(direction)._1)(position._2 + justRight(direction)._2) match {
        case '#' => justRight(justRight(direction))
        case _ => justRight(direction)
    }
}

def move(position: (Int, Int), direction: (Int, Int), stepCount: Int, visitedPositions: Set[(Int, Int)]): (Int, Set[(Int, Int)]) = {
    val triedPosition = (position._1 + direction._1, position._2 + direction._2)

    if (triedPosition._1 < 0 || triedPosition._1 >= inputMatrix.length || triedPosition._2 < 0 || triedPosition._2 >= inputMatrix(0).length) {
        println("Out of bounds")
        (stepCount + 1, visitedPositions)
    } else {
        val triedCell = inputMatrix(triedPosition._1)(triedPosition._2)
        triedCell match {
            case '#' =>
                val newDirection = turnRightDirection(position, direction)
                val newPosition = (position._1 + newDirection._1, position._2 + newDirection._2)
                move(newPosition, newDirection, stepCount + 1, visitedPositions + newPosition)
            case _ => move(triedPosition, direction, stepCount + 1, visitedPositions + triedPosition)
        }
    }
}

val UP = (-1, 0)
val LEFT = (0, -1)
val RIGHT =(0, 1)
val DOWN = (1, 0)
val UPDOWN = Set((-1, 0), (1, 0))
val LEFTRIGHT = Set((0, -1), (0, 1))
val BOTH = UPDOWN ++ LEFTRIGHT

def checkObstruction(position: (Int, Int), direction: (Int, Int), canMove: Map[(Int, Int), Set[(Int, Int)]], obstruction: (Int, Int)): Boolean = {
    val triedPosition = (position._1 + direction._1, position._2 + direction._2)
    if (triedPosition._1 < 0 || triedPosition._1 >= inputMatrix.length || triedPosition._2 < 0 || triedPosition._2 >= inputMatrix(0).length) {
        false
    } else {
        val triedCell = inputMatrix(triedPosition._1)(triedPosition._2)
        (triedCell, triedPosition) match {
            case ('#', _) =>
                val newDirection = turnRightDirection(position, direction)
                val newPosition = (position._1 + newDirection._1, position._2 + newDirection._2)
                if (inputMatrix(newPosition._1)(newPosition._2) != '#') {
                    if (canMove.getOrElse(newPosition, Set.empty).contains(newDirection)) {
                        println("Obstruction found")
                        true
                    } else if (newPosition != obstruction) {
                        val newCanMove = canMove.updated(position, canMove.getOrElse(position, Set.empty) + newDirection)
                        checkObstruction(newPosition, newDirection, newCanMove, obstruction)
                    } else {
                        false
                    }
                } else {
                    false
                }

            case (_, o) if o == obstruction =>
                val newDirection = turnRightDirection(position, direction)
                val newPosition = (position._1 + newDirection._1, position._2 + newDirection._2)
                if (inputMatrix(newPosition._1)(newPosition._2) != '#') {
                    if (canMove.getOrElse(newPosition, Set.empty).contains(newDirection)) {
                        println("Obstruction found")
                        true
                    } else if (newPosition != obstruction) {
                        val newCanMove = canMove.updated(position, canMove.getOrElse(position, Set.empty) + newDirection)
                        checkObstruction(newPosition, newDirection, newCanMove, obstruction)
                    } else {
                        false
                    }
                } else {
                    false
                }

            case _ => 
                if (canMove.getOrElse(triedPosition, Set.empty).contains(direction)) {
                    true
                } else {
                    val newCanMove = canMove.updated(position, canMove.getOrElse(position,Set.empty) + direction)
                    checkObstruction(triedPosition, direction, newCanMove, obstruction)
                }
        }
    }
}

def moveWithObstructions(position: (Int, Int), 
                         direction: (Int, Int), 
                         canMove: Map[(Int, Int), Set[(Int, Int)]],
                         possibleObstructions: Set[(Int, Int)]
    ): Int = {
    val triedPosition = (position._1 + direction._1, position._2 + direction._2)

    if (triedPosition._1 < 0 || triedPosition._1 >= inputMatrix.length || triedPosition._2 < 0 || triedPosition._2 >= inputMatrix(0).length) {
        // println("Out of bounds")
        possibleObstructions.size
    } else {
        val triedCell = inputMatrix(triedPosition._1)(triedPosition._2)
        triedCell match {
            case '#' =>
                val newDirection = turnRightDirection(position, direction)
                val newPosition = (position._1 + newDirection._1, position._2 + newDirection._2)

                if (inputMatrix(newPosition._1)(newPosition._2) != '#') then
                    val newCanMove = canMove.updated(position, canMove.getOrElse(position, Set.empty) + newDirection)
                    moveWithObstructions(newPosition, newDirection, newCanMove, possibleObstructions)
                else 
                    possibleObstructions.size
            case _ => 
                // check obstruction
                val rightDirection = turnRightDirection(position, direction)
                val rightPosition = (position._1 + rightDirection._1, position._2 + rightDirection._2)
                val newCanMove = canMove.updated(position, canMove.getOrElse(position, Set.empty) + direction)
                val rightCanMove = canMove.updated(position, canMove.getOrElse(position, Set.empty) + rightDirection)
                if (
                    !(triedPosition._1 == startingPositionI && triedPosition._2 == startingPositionJ) &&
                    // inputMatrix(rightPosition._1)(rightPosition._2) != '#' &&
                    checkObstruction(rightPosition, rightDirection, rightCanMove, triedPosition)) {

                    val newPossibleObstructions = possibleObstructions + triedPosition

                    moveWithObstructions(triedPosition, direction, newCanMove, newPossibleObstructions)
                } else {
                    moveWithObstructions(triedPosition, direction, newCanMove, possibleObstructions)
                }
        }
    }
}

def printVisitedPositions(visitedPositions: Set[(Int, Int)], possibleObstructions: Set[(Int, Int)], canMoves: Map[(Int, Int), Set[(Int, Int)]]): Unit = {
    for (i <- 0 until inputMatrix.length) {
        for (j <- 0 until inputMatrix(0).length) {
            canMoves.get((i, j)) match {
                case Some(moves) =>
                    if (possibleObstructions.contains((i, j))) {
                        print("O")
                    } else if (moves.contains(UP) || moves.contains(DOWN)) {
                        print("|")
                    } else if (moves.contains(LEFT) || moves.contains(RIGHT)) {
                        print("-")
                    } else if ((moves.contains(UP) || moves.contains(DOWN)) && (moves.contains(LEFT) || moves.contains(RIGHT))) {
                        print("+")
                    } else {
                        print("?")
                    }
                case None =>
                    if (possibleObstructions.contains((i, j))) {
                        print("O")
                    } else if (visitedPositions.contains((i, j))) {
                        print("X")
                    } else {
                        print(inputMatrix(i)(j))
                    }
            }
        }
        println()
    }
}

val (part1Res, visitedPositions) = move((startingPositionI, startingPositionJ), (-1, 0), 0, Set((startingPositionI, startingPositionJ)))

// printVisitedPositions(visitedPositions, Set.empty)

// println(visitedPositions.size)

val part2Res = moveWithObstructions((startingPositionI, startingPositionJ), UP, Map((startingPositionI, startingPositionJ) -> Set(UP)), Set.empty)

println(part2Res)
