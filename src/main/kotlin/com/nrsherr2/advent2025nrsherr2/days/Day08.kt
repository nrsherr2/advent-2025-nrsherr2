package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Connection
import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point3D
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import com.nrsherr2.advent2025nrsherr2.utils.squaredEuclideanDistance
import kotlin.math.pow

@Day(8)
class Day08 : DaySolution {
    override fun testPart1() {
        println("weird ass result: " + part1(parseFile("test/Day08"), 10, true))
    }

    override fun testPart2() {
        val res = part2("test/Day08")
        println("result: $res")
    }

    override fun solvePart1() {
        println("weird ass result: " + part1(parseFile("input/Day08"), 1000))
    }

    override fun solvePart2() {
        val res = part2("input/Day08")
        println("result: $res")
    }

    fun parseFile(path: String): List<Point3D> {
        return readInput(path).map { l -> l.split(",").let { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) } }
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
            if (circuits.all { it.value == circuits[pointA]!! }) {
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

    private fun part1(points: List<Point3D>, maxLen: Int, isTesting: Boolean = false): Int {
        var sortedConnections = listOf<Pair<Connection, Long>>()
        points.forEachIndexed { index, a ->
            points.subList(index + 1, points.size).forEach { b ->
                sortedConnections = sortedConnections.insertSorted(Connection(a, b), maxLen)
            }
        }

        val circuits = points.map { mutableListOf(it) }.toMutableList()
        sortedConnections.forEach { (points, distance) ->
            val circuitContainsA = circuits.indexOfFirst { it.contains(points.first) }
            val circuitContainsB = circuits.indexOfFirst { it.contains(points.second) }
            when {
                circuitContainsA == -1 && circuitContainsB == -1 -> {
                    circuits.add(mutableListOf(points.first, points.second))
                }

                circuitContainsA != -1 && circuitContainsB == -1 -> {
                    circuits[circuitContainsA].add(points.second)
                }

                circuitContainsA == -1 && circuitContainsB != -1 -> {
                    circuits[circuitContainsB].add(points.first)
                }

                circuitContainsA != circuitContainsB -> {
                    val l1 = circuits[circuitContainsA]
                    val l2 = circuits[circuitContainsB]
                    circuits.removeAt(circuitContainsA)
                    val newContainsB = circuits.indexOfFirst { it.contains(points.second) }
                    circuits.removeAt(newContainsB)
                    circuits.add((l1 + l2).toMutableList())
                }
            }
            if (isTesting) println((circuits.size to circuits.map { it.size }))
        }
        println(circuits.size to circuits.map { it.size })
        return circuits.map { it.size }.sortedDescending().take(3).fold(1, Int::times)
    }

    private fun List<Pair<Connection, Long>>.insertSorted(
        n: Connection,
        maxLen: Int? = null
    ): List<Pair<Connection, Long>> {

        val distance = squaredEuclideanDistance(n.first, n.second)

        if (this.isEmpty()) return listOf(n to distance)
        val nl = if (distance >= this.last().second) {
            this + (n to distance)
        } else if (distance <= this.first().second) {
            listOf(n to distance) + this
        } else {
            val indexGreater = this.indexOfFirst { it.second > distance }
            this.subList(0, indexGreater) + (n to distance) + this.subList(indexGreater, this.size)
        }
        return if (maxLen != null) nl.take(maxLen) else nl
    }

    private fun parseInput(path: String) = readInput(path).map {
        val (x, y, z) = it.split(",")
        Point3D(x.toInt(), y.toInt(), z.toInt())
    }
}