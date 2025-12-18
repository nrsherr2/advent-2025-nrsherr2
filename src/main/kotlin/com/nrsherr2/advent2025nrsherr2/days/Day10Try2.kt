package com.nrsherr2.advent2025nrsherr2.days

import com.nrsherr2.advent2025nrsherr2.utils.DaySolution
import com.nrsherr2.advent2025nrsherr2.utils.readInput
import com.nrsherr2.advent2025nrsherr2.utils.Day

//@Day(10)
class Day10Try2 : DaySolution {
    override fun testPart1() {
        val try2 = parseInput("test/Day10")
        println("sum of min presses: ${part1(try2)}")
    }

    override fun testPart2() {
        val try2 = parseInput("test/Day10")
        println("sum of min presses: ${part2(try2)}")
    }

    override fun solvePart1() {
        val try2 = parseInput("input/Day10")
        println("sum of min presses: ${part1(try2)}")
    }

    override fun solvePart2() {
        val try2 = parseInput("input/Day10")
        println("sum of min presses: ${part2(try2)}")
    }

    fun part2(machines: List<Machine>) = machines.sumOf { machine ->
        val combos = allLightCombos(machine)
        Part2Solver(machine.buttons, combos).recurse(machine.goalNums).also { println(it) }
    }

    class Part2Solver(val buttons: List<Button>, val combos: Map<String, Set<String>>) {
        init {
            combos.forEach { (string, strings) -> println("$string -> $strings") }
        }

        val recursionCache: MutableMap<List<Int>, Long> = mutableMapOf()

        fun recurse(goalNums: List<Int>): Long {
            //step one, check cache to see if this has been solved
            recursionCache[goalNums]?.let { return it }

            //step 2, if we've reached base case, return good
            if (goalNums.all { it == 0 }) return 0

            //step 3, check if we've gone too far
            if (goalNums.any { it < 0 }) return Int.MAX_VALUE.toLong()

//            println(goalNums)

            // test if we have pre-computed a solution to this combination
            val nextLevelDown = goalNums.map { if (it.mod(2) == 1) (it - 1) / 2 else it / 2 }
            val asLights = goalNums.map { if (it.mod(2) == 0) '.' else '#' }.joinToString("")


            val candidates = combos[asLights]
            val withBelow = candidates?.let {
                val min = candidates.minOf { c -> c.count { it == '1' } }
                min + (2 * recurse(nextLevelDown))
            } ?: Int.MAX_VALUE.toLong()

            val pressed = buttons.minOf { button ->
                val nextLevelDown =
                    goalNums.mapIndexed { index, num -> if (button.connectedLights.contains(index)) num - 1 else num }
                1 + recurse(nextLevelDown)
            }
            if (withBelow <= pressed) {
                recursionCache[goalNums] = withBelow
                if (recursionCache.size.mod(10_000) == 0) println(recursionCache.size)
//                println("$goalNums -> $withBelow")
                return withBelow
            } else {
                recursionCache[goalNums] = pressed
                if (recursionCache.size.mod(10_000) == 0) println(recursionCache.size)
//                println("$goalNums -> $pressed")
                return pressed
            }
        }
    }

    fun part1(machines: List<Machine>) = machines.sumOf { machine ->
        val combos = allLightCombos(machine)
        val solAsString = machine.goalLights.joinToString("") { if (it) "#" else "." }
        val matchingCombo = combos[solAsString]!!
        matchingCombo.minOf { c -> c.count { it == '1' } }
    }

    fun parseInput(path: String): List<Machine> {
        return readInput(path).map { l ->
            val lights = l.split(" ").first().trim { it !in listOf('#', '.') }.map { it == '#' }
            val nums = l.split(" ").last().trim { it !in '0'..'9' }.split(",").map { it.toInt() }
            val buttons = l.split(" ").subList(1, l.split(" ").size - 1).map { bs ->
                Button(bs.trim { it !in '0'..'9' }.split(",").map { it.toInt() })
            }
            Machine(lights, buttons, nums)
        }
    }

    fun allLightCombos(m: Machine): Map<String, Set<String>> {
        val maxNumber = m.buttons.map { '1' }.joinToString("").toLong(2)
        return buildMap<String, MutableSet<String>> {
            for (i in (0..maxNumber)) {
                val buttonsPressed = i.toString(2).padStart(m.buttons.size, '0')
                val lightState = buildState(buttonsPressed, m.buttons, m.goalNums.size)
//                println("$i: $buttonsPressed -> $lightState")
                if (containsKey(lightState)) {
                    get(lightState)!!.add(buttonsPressed)
                } else {
                    put(lightState, mutableSetOf(buttonsPressed))
                }
            }
        }
    }

    fun buildState(buttonsPressed: String, buttons: List<Button>, lightLen: Int): String {
        val lights = List(lightLen) { false }.toMutableList()
        buttonsPressed.forEachIndexed { index, ch ->
            if (ch == '1') {
                val button = buttons[index]
                button.connectedLights.forEach { light -> lights[light] = !lights[light] }
            }
        }
        return lights.map { if (it) '#' else '.' }.joinToString("")
    }

    data class Machine(
        val goalLights: List<Boolean>,
        val buttons: List<Button>,
        val goalNums: List<Int>
    )

    data class Button(val connectedLights: List<Int>)
}