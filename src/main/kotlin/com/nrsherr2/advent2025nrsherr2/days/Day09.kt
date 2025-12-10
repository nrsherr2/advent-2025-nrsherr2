package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import kotlin.math.abs
import kotlin.math.max

@Day(9)
class Day09 : DaySolution {
    override fun testPart1() {
        println(part1(readInput("test/Day09")))
    }

    override fun testPart2() {
    }

    override fun solvePart1() {
        println(part1(readInput("input/Day09")))
    }

    override fun solvePart2() {
    }

    private fun part1(readInput: List<String>): Long {
        val points = readInput.map {
            val (x, y) = it.split(",")
            Point(y.toInt(), x.toInt())
        }
        var maxArea = 0L
        for(i in points.indices) {
            val pointA = points[i]
            for(j in i+1 until points.size) {
                val pointB = points[j]
                val area = abs( pointA.x - pointB.x + 1L) * abs( pointA.y - pointB.y+ 1L)
                maxArea = max(maxArea, area)
            }
        }
        return maxArea
    }

}