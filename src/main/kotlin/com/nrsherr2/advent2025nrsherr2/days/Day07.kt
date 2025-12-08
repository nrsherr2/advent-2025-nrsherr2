package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.readInput

@Day(7)
class Day07 : DaySolution {
    override fun testPart1() {
        println("num splits: ${part1(readInput("test/Day07"))}")
    }

    override fun testPart2() {
        println("num worlds: ${part2(readInput("test/Day07"))}")
    }

    override fun solvePart1() {
        println("num splits: ${part1(readInput("input/Day07"))}")
    }

    override fun solvePart2() {
        println("num worlds: ${part2(readInput("input/Day07"))}")
    }

    fun part2(input: List<String>): Long {
        val instances = mutableMapOf<Int, Long>()
        input.forEach { line ->
            line.forEachIndexed { idx, char ->
                when (char) {
                    'S'-> instances[idx] = instances.getOrDefault(idx, 0) + 1
                    '^' -> {
                        val count = instances.getOrDefault(idx, 0)
                        instances[idx] = 0
                        instances[idx-1] = instances.getOrDefault(idx-1,0) + count
                        instances[idx+1] = instances.getOrDefault(idx+1,0) + count
                    }
                }
            }
        }
        return instances.entries.sumOf { it.value }
    }

    fun part1(input: List<String>): Int {
        val beams = mutableMapOf<Int, Boolean>()
        var acc = 0
        input.forEach { line ->
            line.forEachIndexed { idx, char ->
                when (char) {
                    'S' -> {
                        beams[idx] = true
                    }

                    '^' -> if (beams.getOrDefault(idx, false)) {
                        beams[idx] = false
                        beams[idx + 1] = true
                        beams[idx - 1] = true
                        acc++
                    }
                }
            }
        }
        return acc
    }
}