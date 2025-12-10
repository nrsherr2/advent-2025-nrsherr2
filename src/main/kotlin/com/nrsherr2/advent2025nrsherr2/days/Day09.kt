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
    }

    private fun part2(readInput: List<String>): Long {
        val points = readInput.map {
            val (x, y) = it.split(",")
            Point(y.toInt(), x.toInt())
        }

        val rectangles = (points + listOf(points[0], points[1])).windowed(3, 1).map { (a, b, c) ->
            Rectangle(a, b, c)
        }

        var maxArea = 0L
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]
                val testRectangle = Rectangle(
                    point1 = p1,
                    point3 = p2,
                    point2 = Point(0, 0),
                )
                val isContained = testRectangle.getPoints().all { point -> rectangles.any { it.contains(point) } }

            }
        }

        return -1
    }

    private class Rectangle(point1: Point, point2: Point, point3: Point) {
        val xRange = min(point1.x, point3.x)..max(point1.x, point3.x)
        val yRange = min(point1.y, point3.y)..max(point1.y, point3.y)
//        val xRange: IntRange =
//            if (point1.x == point2.x)
//            min(point2.x, point3.x)..max(point2.x, point3.x)
//        else
//            min(point1.x, point2.x)..max(point1.x, point2.x)
//        val yRange: IntRange = if (point1.y == point2.y)
//            min(point2.y, point3.y)..max(point2.y, point3.y)
//        else
//            min(point1.y, point2.y)..max(point1.y, point2.y)

        fun contains(point: Point): Boolean {
            return point.x in xRange && point.y in yRange
        }

        fun getPoints(): Sequence<Point> = sequence {
            for (x in xRange) {
                for (y in yRange) {
                    yield(Point(y, x))
                }
            }
        }
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