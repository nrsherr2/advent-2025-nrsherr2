package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Day(9)
class Day09 : DaySolution {
    override fun testPart1() {
        println(part1(readInput("test/Day09")))
    }

    override fun testPart2() {
        println(part2(readInput("test/Day09")))
    }

    override fun solvePart1() {
        println(part1(readInput("input/Day09")))
    }

    override fun solvePart2() {
        println(part2(readInput("input/Day09")))
    }

    class Rectangle(pointA: Point, pointB: Point) {
        val innerMinY = min(pointA.y, pointB.y) + 1
        val innerMaxY = max(pointA.y, pointB.y)
        val innerMinX = (min(pointA.x, pointB.x) + 1)
        val innerMaxX = max(pointA.x, pointB.x)
//        val inner = buildList {
//            for (y in innerMinY..<innerMaxY) {
//                for (x in innerMinX..<innerMaxX) {
//                    add(Point(y, x))
//                }
//            }
//        }

        fun intersects(l: LineSegment): Boolean {
            if (l.pointA.y == l.pointB.y) {
                if (l.pointA.y !in innerMinY..innerMaxY) {
                    return false
                }

                val minX = min(l.pointA.x, l.pointB.x)
                val maxX = max(l.pointA.x, l.pointB.x)

                return when(minX){
                    in Int.MIN_VALUE..<innerMinX ->maxX >= innerMinX
                    in innerMinX..innerMaxX-> true
                    else -> false
                }
            } else if (l.pointB.x == l.pointA.x) {
                if (l.pointB.x !in innerMinX..innerMaxX) {
                    return false
                }

                val minY = min(l.pointA.y, l.pointB.y)
                val maxY = max(l.pointA.y, l.pointB.y)

                return when(minY){
                    in Int.MIN_VALUE..<innerMinY ->maxY >= innerMinY
                    in innerMinY..innerMaxY-> true
                    else -> false
                }
            } else {
                throw IllegalArgumentException()
//                return l.points.any { it in inner }
            }
            return false
        }
    }

    class LineSegment(val pointA: Point, val pointB: Point) {
//        val points = buildList {
//            for (y in min(pointA.y, pointB.y)..max(pointA.y, pointB.y)) {
//                for (x in min(pointA.x, pointB.x)..max(pointA.x, pointB.x)) {
//                    add(Point(y, x))
//                }
//            }
//        }
    }

    private fun part2(readInput: List<String>): Long {
        val points = readInput.map {
            val (x, y) = it.split(",")
            Point(y.toInt(), x.toInt())
        }
        val lines = (points + points.first()).windowed(2, 1).map { (a, b) -> LineSegment(a, b) }

        var maxArea = 0L
        for (i in points.indices) {
            val pointA = points[i]
            for (j in i + 1 until points.size) {
                val pointB = points[j]
                val area = (abs(pointA.x - pointB.x) + 1L) * (abs(pointA.y - pointB.y) + 1L)
                println("testing rect with area: $area")
                val rect = Rectangle(pointA, pointB)
                println(".")
                if (lines.none {
//                        println("testing line with area ${(abs(it.pointA.x - it.pointB.x) + 1L) * (abs(it.pointA.y - it.pointB.y) + 1L)}")
                        rect.intersects(it)
                    }) {
                    maxArea = max(maxArea, area)
                }
                println(".")
            }
        }
        return maxArea
    }


    private fun part1(readInput: List<String>): Long {
        val points = readInput.map {
            val (x, y) = it.split(",")
            Point(y.toInt(), x.toInt())
        }
        var maxArea = 0L
        for (i in points.indices) {
            val pointA = points[i]
            for (j in i + 1 until points.size) {
                val pointB = points[j]
                val area = (abs(pointA.x - pointB.x) + 1L) * (abs(pointA.y - pointB.y) + 1L)
                maxArea = max(maxArea, area)
            }
        }
        return maxArea
    }

}