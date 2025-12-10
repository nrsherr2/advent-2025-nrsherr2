package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point3D
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import com.nrsherr2.advent2025nrsherr2.utils.squaredEuclideanDistance

@Day(8)
class Day08 : DaySolution {
    override fun testPart1() {
        println("weird ass result: " + part1(parseFile("test/Day08"), 10,true))
    }

    override fun testPart2() {
        println("weirder ass result: " + part2(parseFile("test/Day08")))
    }

    override fun solvePart1() {
        println("weird ass result: " + part1(parseFile("input/Day08"), 1000))
    }

    override fun solvePart2() {
    }

    fun parseFile(path: String): List<Point3D> {
        return readInput(path).map { l -> l.split(",").let { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) } }
    }

    private data class Connection(val a: Point3D, val b: Point3D)

    private fun part2(points: List<Point3D>): Int {

        var sortedConnections = listOf<Pair<Connection, Long>>()
        points.forEachIndexed { index, a ->
            points.subList(index + 1, points.size).forEach { b ->
                sortedConnections = sortedConnections.insertSorted(Connection(a, b))
            }
        }

        val circuits = points.map{mutableListOf(it)}.toMutableList()
        sortedConnections.forEach { (yPoints, distance) ->
            val circuitContainsA = circuits.indexOfFirst { it.contains(yPoints.a) }
            val circuitContainsB = circuits.indexOfFirst { it.contains(yPoints.b) }
            when {
                circuitContainsA == -1 && circuitContainsB == -1 -> {
                    circuits.add(mutableListOf(yPoints.a, yPoints.b))
                }

                circuitContainsA != -1 && circuitContainsB == -1 -> {
                    circuits[circuitContainsA].add(yPoints.b)
                }

                circuitContainsA == -1 && circuitContainsB != -1 -> {
                    circuits[circuitContainsB].add(yPoints.a)
                }

                circuitContainsA != circuitContainsB -> {
                    val l1 = circuits[circuitContainsA]
                    val l2 = circuits[circuitContainsB]
                    circuits.removeAt(circuitContainsA)
                    val newContainsB = circuits.indexOfFirst { it.contains(yPoints.b) }
                    circuits.removeAt(newContainsB)
                    circuits.add((l1 + l2).toMutableList())
                }
            }
            println(circuits.size to  circuits.map { it.size })
            if(circuits.size == 1 && circuits[0].size == points.size) {
                println("WE HAVE A MATCH")
                val res = yPoints.a.x * yPoints.b.x
                println("${yPoints.a.x} * ${yPoints.b.x} = $res")
                println(circuits.map { it.size })
                return res
            }
        }
        return -1
    }

    private fun part1(points: List<Point3D>, maxLen: Int, isTesting:Boolean = false): Int {
        var sortedConnections = listOf<Pair<Connection, Long>>()
        points.forEachIndexed { index, a ->
            points.subList(index + 1, points.size).forEach { b ->
                sortedConnections = sortedConnections.insertSorted(Connection(a, b), maxLen)
            }
        }

        val circuits = points.map{mutableListOf(it)}.toMutableList()
        sortedConnections.forEach { (points, distance) ->
            val circuitContainsA = circuits.indexOfFirst { it.contains(points.a) }
            val circuitContainsB = circuits.indexOfFirst { it.contains(points.b) }
            when {
                circuitContainsA == -1 && circuitContainsB == -1 -> {
                    circuits.add(mutableListOf(points.a, points.b))
                }

                circuitContainsA != -1 && circuitContainsB == -1 -> {
                    circuits[circuitContainsA].add(points.b)
                }

                circuitContainsA == -1 && circuitContainsB != -1 -> {
                    circuits[circuitContainsB].add(points.a)
                }

                circuitContainsA != circuitContainsB -> {
                    val l1 = circuits[circuitContainsA]
                    val l2 = circuits[circuitContainsB]
                    circuits.removeAt(circuitContainsA)
                    val newContainsB = circuits.indexOfFirst { it.contains(points.b) }
                    circuits.removeAt(newContainsB)
                    circuits.add((l1 + l2).toMutableList())
                }
            }
            if(isTesting)println((circuits.size to  circuits.map { it.size }))
        }
        println(circuits.size to  circuits.map { it.size })
        return circuits.map { it.size }.sortedDescending().take(3).fold(1, Int::times)
    }

    private fun List<Pair<Connection, Long>>.insertSorted(n: Connection, maxLen: Int?=null): List<Pair<Connection, Long>> {

        val distance = squaredEuclideanDistance(n.a, n.b)

        if (this.isEmpty()) return listOf(n to distance)
        val nl = if (distance >= this.last().second) {
            this + (n to distance)
        } else if (distance <= this.first().second) {
            listOf(n to distance) + this
        } else {
            val indexGreater = this.indexOfFirst { it.second > distance }
            this.subList(0, indexGreater) + (n to distance) + this.subList(indexGreater, this.size)
        }
        return if(maxLen != null) nl.take(maxLen) else nl
    }
}