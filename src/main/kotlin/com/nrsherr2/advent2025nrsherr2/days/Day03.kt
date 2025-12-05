package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.Day
import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.readInput

@Day(3)
class Day03 : DaySolution {
    override fun testPart1() {
        val soln = parseTest().sumOf { part1(it) }
        println("*** Part 1 sum: $soln")
    }

    override fun testPart2() {
        val soln = parseTest().sumOf { part2(it) }
        println("*** Part 2 sum: $soln")
    }

    override fun solvePart1() {
        val soln = parseInput().sumOf { part1(it) }
        println("*** Part 1 sum: $soln")
    }

    /**
     * BOAT MODE FUCK YEAH
     */
    override fun solvePart2() {
//        val soln = parseInput().sumOf { part2(it) }
//        println("*** Part 2 sum: $soln")

    }

    private fun part1(bank: String): Int {
        val locations = buildMap<Int, List<Int>> {
            bank.forEachIndexed { index, jolt ->
                val i = jolt.digitToInt()
                put(i, (get(i) ?: emptyList()).plus(index))
            }
        }
        val revved = locations.toSortedMap().reversed()
        revved.forEach { (number, indices) ->
            val earliestIndex = indices.min()
            print("$number at $earliestIndex | ")
            for (i in 9 downTo 1) {
                val hasNumberAfter = locations[i]?.firstOrNull { it > earliestIndex }
                if (hasNumberAfter != null) {
                    print("resolves with $i at $hasNumberAfter | ")
                    println("$number$i".toInt())
                    return "$number$i".toInt()
                }
            }
        }
        return 1
    }

    private fun part2(line: String): Long {
        data class Node(val value: Int, val index: Int)
        data class NodeStr(val value: Int, val index: Int, val str: String) {
            fun absorb(n: Node): NodeStr = NodeStr(n.value, n.index, str + n.value)
        }

        val nodes = line.mapIndexed { index, c -> Node(c.digitToInt(), index) }
        var currentLayer = setOf(NodeStr(0, -1, ""))

        (1..12).forEach { i->
            val nextLayer = mutableSetOf<NodeStr>()
//        println("Round $i: ${currentLayer.size} nodes")
//        if(currentLayer.size < 10) println(currentLayer)
            currentLayer.forEach { curr->
                (1..9).forEach { num ->
                    nodes.filter { it.value == num && it.index > curr.index }.minByOrNull { it.index }?.let { candidate ->
                        val ab = curr.absorb(candidate)
//                    println("$curr -> $ab")
                        nextLayer.add(ab)
                    }
                }
            }
            currentLayer = nextLayer.sortedByDescending { it.str.toLong() }.take(250).toSet()
        }
//        println("current size: ${currentLayer.size}")
//    println(currentLayer.maxBy { it.str.toLong() })
        return currentLayer.maxOf { it.str.toLong() }
    }



    private fun parseTest() = readInput("test/Day03")
    private fun parseInput() = readInput("input/Day03")
}