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
        printGridWithAllRectangles(rectangles)

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
                printTestRectangleComparison(rectangles, testRectangle)
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

    /**
     * Function 1: Print out the grid made by all rectangles in the rectangle list.
     * If a point is not covered by one of the rectangles, print a '.' otherwise, print '@'.
     */
    private fun printGridWithAllRectangles(rectangles: List<Rectangle>) {
        // Find the bounds of all rectangles
        val allXValues = rectangles.flatMap { listOf(it.xRange.first, it.xRange.last) }
        val allYValues = rectangles.flatMap { listOf(it.yRange.first, it.yRange.last) }

        if (allXValues.isEmpty() || allYValues.isEmpty()) {
            println("No rectangles to display")
            return
        }

        val minX = 0
        val maxX = allXValues.max()
        val minY =0
        val maxY = allYValues.max()

        // Print the grid
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val point = Point(y, x)
                val isInAnyRectangle = rectangles.any { it.contains(point) }
                print(if (isInAnyRectangle) '@' else '.')
            }
            println() // New line after each row
        }
        println()
    }

    /**
     * Function 2: Testing a new rectangle against the rectangle list.
     * If a point is in rectangles only, print '@'.
     * If a point is in the test rectangle only, print '0'.
     * If it's in both, print '#'.
     */
    private fun printTestRectangleComparison(rectangles: List<Rectangle>, testRectangle: Rectangle) {
        // Find the bounds that encompass both the rectangles and the test rectangle
        val allRectangles = rectangles + testRectangle
        val allXValues = allRectangles.flatMap { listOf(it.xRange.first, it.xRange.last) }
        val allYValues = allRectangles.flatMap { listOf(it.yRange.first, it.yRange.last) }

        if (allXValues.isEmpty() || allYValues.isEmpty()) {
            println("No rectangles to compare")
            return
        }

        val minX = 0
        val maxX = allXValues.max()
        val minY = 0
        val maxY = allYValues.max()

        // Print the grid
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val point = Point(y, x)
                val isInExistingRectangles = rectangles.any { it.contains(point) }
                val isInTestRectangle = testRectangle.contains(point)

                val char = when {
                    isInExistingRectangles && isInTestRectangle -> '#' // In both
                    isInExistingRectangles -> '@' // In existing rectangles only
                    isInTestRectangle -> '0' // In test rectangle only
                    else -> '.' // In neither
                }
                print(char)
            }
            println() // New line after each row
        }
        println()
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