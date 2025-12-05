package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import kotlin.math.abs

@Day(1)
class Day01 : DaySolution {
    override fun testPart1() {
        val numTimes = part1(parseTest())
        println("*** Test part 1: hits 0 $numTimes times ***")
    }

    override fun testPart2() {
        val numTimes = part2(parseTest())
        println("*** Test part 2: hits 0 $numTimes times ***")
    }

    override fun solvePart1() {
        val numTimes = part1(parseReal())
        println("*** Answer part 1: hits 0 $numTimes times ***")
    }

    override fun solvePart2() {
        val numTimes = part2(parseReal())
        println("*** Answer part 2: hits 0 $numTimes times ***")
    }

    private fun part1(input: List<String>): Int {
        val results = mutableListOf<Int>(50)
        input.forEach { action ->
            val distance = action.substring(1).toInt().let { if (action.startsWith("L")) it * -1 else it }
            results.add((results.last() + distance) % 100)
        }
        println(results)
        return results.count { it == 0 }
    }

    private fun part2(input: List<String>): Int {
        var count = 0
        var lastRes = 50
        input.forEach { action ->
            val distance = action.substring(1).toInt()

            run {
                val distanceHundreds = distance / 100
                if (distanceHundreds > 0) println("$action click $distanceHundreds")
            }
            count += distance / 100

            val distanceTens = distance % 100
            val tst = if(action.startsWith("L")) (lastRes - distanceTens) else (lastRes + distanceTens)
            if(lastRes == 0){
                println("$action 0 -> $tst")
                lastRes = (100 + tst) % 100
            }else {
                val tRes = lastRes + 0
                when(tst) {
                    0 -> {
                        count++
                        lastRes = 0
                    }
                    in Int.MIN_VALUE.. -100, in 199..Int.MAX_VALUE -> {
                        throw IllegalArgumentException("SHOULD NOT GET THIS NUMBER: $tst")
                    }
                    in -99.. -1 -> {
                        count++
                        lastRes = 100 + tst
                    }
                    in 100..198 -> {
                        count++
                        lastRes = tst % 100
                    }
                    else -> lastRes = tst
                }
                println("$action $tRes -> $lastRes")
            }
        }
        return count
    }

    fun parseTest(): List<String> {
        return readInput("/test/Day01")
    }

    fun parseReal(): List<String> {
        return readInput("/input/Day01")
    }
}