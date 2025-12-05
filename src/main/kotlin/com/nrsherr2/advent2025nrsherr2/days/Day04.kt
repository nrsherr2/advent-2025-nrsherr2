package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point
import com.nrsherr2.advent2025nrsherr2.utils.neighborCoords
import com.nrsherr2.advent2025nrsherr2.utils.readInput

@Day(4)
class Day04 : DaySolution {
    override fun testPart1() {
        val res = part1(readInput("test/Day04"))
        println("4p1Test: $res")
    }

    override fun testPart2() {
        val res = part2(readInput("test/Day04"))
        println("4p2Test: $res")
    }

    override fun solvePart1() {
        val res = part1(readInput("input/Day04"))
        println("4p1Solve: $res")
    }

    override fun solvePart2() {

        val res = part2(readInput("input/Day04"))
        println("4p2Solve: $res")
    }

    private fun part2(input: List<String>): Int {
        val coordScores = mutableMapOf<Point, Int>()
        val rolls = mutableListOf<Point>()
        val numRows = input.size
        val numCols = input[0].length
        input.forEachIndexed { rowNum, line ->
            line.forEachIndexed lineIter@{ colNum, char ->
                if (char == '.') return@lineIter
                else if (char != '@') throw IllegalArgumentException()
                else {
                    val newPoint = Point(rowNum, colNum)
                    rolls.add(newPoint)
                    newPoint.neighborCoords(numRows, numCols).toList(includeObj = false).forEach { neighbor ->
                        coordScores[neighbor] = coordScores.getOrDefault(neighbor, 0) + 1
                    }
                }

            }
        }
        var acc = 1
        var numRemoved = 0
        while (rolls.any { coordScores.getOrDefault(it, 0) < 4 }) {
            println("round $acc")
//            printScores(numRows,numCols,rolls,coordScores)
//            printBoard(numRows,numCols,rolls,coordScores)
            var removedThisRound = 0
            val neigborsToRemovePointsFrom = rolls
                .filter { coordScores.getOrDefault(it, 0) < 4 }
                .onEach {
                    rolls.remove(it)
                    removedThisRound++
                }
                .flatMap { it.neighborCoords(numRows, numCols).toList(includeObj = false) }
            neigborsToRemovePointsFrom.forEach {
                val currentScore = coordScores.getOrDefault(it, 0)
                if(currentScore == 0) throw IllegalArgumentException("subtracting 1 from 0 at $it!")
                coordScores[it] = currentScore - 1
            }
            println("removed $removedThisRound")
            numRemoved += removedThisRound
            acc++
        }
//        printBoard(numRows, numCols, rolls, coordScores)
        return numRemoved
    }


    private fun part1(input: List<String>): Int {
        val coordScores = mutableMapOf<Point, Int>()
        val rolls = mutableListOf<Point>()
        val numRows = input.size
        val numCols = input[0].length
        input.forEachIndexed { rowNum, line ->
            line.forEachIndexed lineIter@{ colNum, char ->
                if (char == '.') return@lineIter
                else if (char != '@') throw IllegalArgumentException()
                else {
                    val newPoint = Point(rowNum, colNum)
                    rolls.add(newPoint)
                    newPoint.neighborCoords(numRows, numCols).toList(includeObj = false).forEach { neighbor ->
                        coordScores[neighbor] = coordScores.getOrDefault(neighbor, 0) + 1
                    }
                }
            }
        }

//        printScores(numRows, numCols, rolls, coordScores)
//        printBoard(numRows, numCols, rolls, coordScores)
        return rolls.count { coordScores.getOrDefault(it, 0) < 4 }
    }

    private fun printScores(
        numRows: Int,
        numCols: Int,
        rolls: MutableList<Point>,
        coordScores: MutableMap<Point, Int>
    ) {
        for (row in 0..<numRows) {
            for (col in 0..<numCols) {
                if (rolls.contains(Point(row, col))) {
                    print(coordScores.getOrDefault(Point(row, col), 0))
                } else {
                    print('.')
                }
            }
            println()
        }
        println("\n***\n")
    }

    private fun printBoard(
        numRows: Int,
        numCols: Int,
        rolls: MutableList<Point>,
        coordScores: MutableMap<Point, Int>
    ) {
        for (row in 0..<numRows) {
            for (col in 0..<numCols) {
                if (rolls.contains(Point(row, col))) {
                    if (coordScores.getOrDefault(Point(row, col), 0) < 4) {
                        print('x')
                    } else {
                        print('@')
                    }
                } else {
                    print('.')
                }
            }
            println()
        }
        println("\n***\n")
    }


}