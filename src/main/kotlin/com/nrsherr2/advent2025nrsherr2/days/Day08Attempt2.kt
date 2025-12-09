package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point3D
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import kotlin.math.pow

@Day(8)
class Day08Attempt2 : DaySolution {
    override fun testPart1() {
        println("skip")
    }

    override fun testPart2() {
        val res =part2("test/Day08")
        println("result: $res")
    }

    override fun solvePart1() {
        println("skip")
    }

    override fun solvePart2() {
        val res =part2("input/Day08")
        println("result: $res")
    }

    private fun part2(path: String): Int {
        val points = parseInput(path)
        val circuits = points.mapIndexed { index, point -> point to index }.toMap().toMutableMap()
        val distanceMap = buildDistanceMap(points).toMutableMap()
        val sortedDistanceList = distanceMap.toList().sortedBy { (_, value) -> value }
        sortedDistanceList.forEach { (conn, _) ->
            val (pointA, pointB) = conn
            if (circuits[pointA] == circuits[pointB]) return@forEach
            val allB = circuits.filter { it.value == circuits[pointB] }
            allB.forEach { circuits[it.key] = circuits[pointA]!! }
            if(circuits.all { it.value == circuits[pointA]!! }) {
                println("WE HAVE A MATCH AT $conn")
                return pointA.rowNum * pointB.rowNum
            }
        }
        return -1
    }


    private fun buildDistanceMap(points: List<Point3D>): Map<Connection, Long> {
        return buildMap {
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val pointA = points[i]
                    val pointB = points[j]
                    val distance = (pointB.x.toLong() - pointA.x).toDouble().pow(2).toLong() +
                            (pointB.y.toLong() - pointA.y).toDouble().pow(2).toLong() +
                            (pointB.z.toLong() - pointA.z).toDouble().pow(2).toLong()
                    put(Connection(pointA, pointB), distance)
                }
            }
        }
    }

    private fun parseInput(path: String) = readInput(path).map {
        val (x, y, z) = it.split(",")
        Point3D(x.toInt(), y.toInt(), z.toInt())
    }
}

typealias Connection = Pair<Point3D, Point3D>