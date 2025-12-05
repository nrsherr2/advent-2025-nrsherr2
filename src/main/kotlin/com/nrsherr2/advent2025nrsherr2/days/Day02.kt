package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import kotlin.math.pow

@Day(2)
class Day02 : DaySolution {
    override fun testPart1() {
        val input = parseTest()
        println("*** Test part 1: sums to ${part1(input)} ***")
    }

    override fun testPart2() {
        val input = parseTest()
        println("*** Part 2: sums to ${part2(input)} ***")
    }

    override fun solvePart1() {
        val input = parseInput()
        println("*** Part 1: sums to ${part1(input)} ***")
    }

    override fun solvePart2() {
        val input = parseInput()
        println("*** Part 2: sums to ${part2(input)} ***")
    }

    private fun part1(input: String): Long {
        val ranges = getRanges(input)
        return ranges.sumOf { (lo, hi) ->
            val mirrorHalfLo = findNextMirrorableUp(lo)
            val mirrorHalfHi = findNextMirrorableDown(hi)
            val allInvalid = (mirrorHalfLo..mirrorHalfHi)
                .map { "$it$it".toLong() }
                .filter { it in lo..hi }

            println("($lo,$hi) -> $allInvalid")
            allInvalid.sum()
        }
    }

    private fun part2(input: String): Long {
        val ranges = getRanges(input)
        return ranges.sumOf { (lo, hi) ->
            val invalids = buildSet<Long> {
                for(substringLength in 1..lo.toString().length){
                    val minNumber = 10.0.pow((substringLength-1).toDouble()).toLong()
                    for(numRepeats in 2..hi.toString().length ){
                        if((substringLength * numRepeats) !in (lo.toString().length..hi.toString().length)) continue
                        for(i in minNumber..<minNumber*10){
                            val tst = i.toString().repeat(numRepeats).toLong()
                            if(tst in lo..hi){
                                add(tst)
                            }
                        }
                    }
                }
            }

            println("($lo,$hi) -> $invalids")
            invalids.sum()
        }
    }

    private fun findNextMirrorableUp(num: Long): Long {
        val digits = num.toString().length
        return (if (digits % 2 == 0) {
            num
        } else {
            10.0.pow((digits).toDouble()).toLong()
        }).let { mirrorable ->
            val s = mirrorable.toString()
            s.take(s.length / 2).toLong()
        }

    }

    private fun findNextMirrorableDown(num: Long): Long {
        val digits = num.toString().length
        return (if (digits % 2 == 0) {
            num
        } else {
            10.0.pow((digits - 1).toDouble()).toLong() - 1
        }).let { mirrorable ->
            val s = mirrorable.toString()
            s.take(s.length / 2).toLong()
        }
    }

    private fun getRanges(input: String): List<Pair<Long,Long>> {
        return input.split(",").map { s -> s.substringBefore("-").toLong() to s.substringAfter("-").toLong() }
    }

    private fun parseTest(): String {
        return readInput("test/Day02").joinToString("")
    }

    private fun parseInput():String{return readInput("input/Day02").joinToString("")}
}