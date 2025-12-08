package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.readInput

@Day(6)
class Day06 : DaySolution {
    override fun testPart1() {
        val input = parseFile("test/Day06")
        val res = part1(input)
        println("Total sum: ${res}\n")
    }

    override fun testPart2() {
        println("total: ${part2("test/Day06")}")
    }

    override fun solvePart1() {
        val input = parseFile("input/Day06")
        val res = part1(input)
        println("Total sum: ${res}\n")
    }

    override fun solvePart2() {
        println("total: ${part2("input/Day06")}")

    }

    private fun part2(path: String): Long {
        val input = readInput(path)
        val longestLineLength = input.maxOf { it.lastIndex }
        val numberLines = input.take(input.size - 1)
        val operandLine = input.last()
        val parsedNumbers = mutableListOf<Long>()
        var acc = 0L
        (longestLineLength downTo 0).forEach { index ->
            val number = numberLines.map { l -> l.getOrElse(index) { ' ' } }
                .joinToString("")
                .trim()
                .also { if (it.isBlank()) return@forEach }
                .toLong()
            parsedNumbers.add(number)
            operandLine.getOrNull(index)?.let { c ->
                when (c) {
                    '+' -> {
                        println("${parsedNumbers.joinToString(" + ")} = ${parsedNumbers.sum()}")
                        acc += parsedNumbers.sum()
                        parsedNumbers.clear()
                    }

                    '*' -> {
                        val t= parsedNumbers.fold(1, Long::times)
                        println("${parsedNumbers.joinToString(" * ")} = $t")
                        acc +=t
                        parsedNumbers.clear()
                    }

                    !in listOf(' ') -> throw IllegalArgumentException("Unexpected character $c")
                }
            }
        }
        return acc
    }

    private fun part1(problems: List<Problem>) = problems.sumOf { it.solve() }

    data class Problem(
        var operands: List<Long>,
        var operation: Char
    ) {
        fun solve(): Long = when (operation) {
            '+' -> operands.sum()
            '*' -> operands.foldRight(1, Long::times)
            else -> throw IllegalArgumentException("Invalid operation: $this")
        }
    }

    fun parseFile(path: String): List<Problem> {
        val lines = readInput(path)
        val problems = lines.last()
            .split(' ')
            .filter { it.isNotBlank() }
            .map { it.first() }
            .map { Problem(listOf(), it) }
        lines.take(lines.size - 1).forEach { line ->
            line.split(' ').filter { it.isNotBlank() }.forEachIndexed { index, s ->
                problems[index].operands += s.toLong()
            }
        }
        return problems
    }
}