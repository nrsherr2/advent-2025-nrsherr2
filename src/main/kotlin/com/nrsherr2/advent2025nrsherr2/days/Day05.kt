package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.Point
import com.nrsherr2.advent2025nrsherr2.utils.neighborCoords
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import kotlin.math.max

@Day(5)
class Day05 : DaySolution {
    override fun testPart1() {
        println("Spoiled Ingredients: ${part1("test/Day05")}")
    }

    override fun testPart2() {
        println("num good ingredients: ${part2("test/Day05")}")
    }

    override fun solvePart1() {
        println("Spoiled Ingredients: ${part1("input/Day05")}")
    }

    override fun solvePart2() {
        println("num good ingredients: ${part2("input/Day05")}")

    }

    private fun part2(filename: String): Long {
        val (rangeStrings, _) = parseInput(filename)
        val ranges = rangeStrings
            .map { it.substringBefore('-').toLong()..it.substringAfter('-').toLong() }
            .sortedBy { it.first }
            .toMutableList()
        val allRanges = mutableListOf<LongRange>()
        while(ranges.isNotEmpty()) {
            var currentRange = ranges.removeAt(0) //pop the first one
            println(currentRange)
            var continueLooping = true
            while(continueLooping) {
                if(ranges.isEmpty()) break
                val nextRange = ranges[0]
                if(nextRange.first <= currentRange.last) {
                    currentRange = currentRange.first..(max(nextRange.last, currentRange.last))
                    println("becomes $currentRange")
                    ranges.removeAt(0)
                } else continueLooping = false
            }
            allRanges.add(currentRange)
        }
        println(allRanges)
        return allRanges.sumOf { it.last - it.first + 1L }
    }

    private fun part1(fileName: String): Int {
        val (rangeStrings, items) = parseInput(fileName)
        val ranges = rangeStrings.map { it.substringBefore('-').toLong()..it.substringAfter('-').toLong() }
        return items.count {
            val i = it.toLong()
            ranges.any { r -> i in r }
        }
    }

    private fun parseInput(fileName: String): Pair<List<String>, List<String>> {
        val lines = readInput(fileName)
        val split = lines.indexOf("")
        return lines.subList(0, split) to lines.subList(split + 1, lines.size)
    }

}