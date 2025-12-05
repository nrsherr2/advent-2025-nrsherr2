package com.nrsherr2.advent2025nrsherr2.utils

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation

/**
 * Spring component that automatically discovers and runs all @Day annotated classes
 * in ascending order when the application starts.
 */
@Component
class DayRunner : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        println("🎄 Starting Advent of Code 2025 Solutions 🎄")
        println("=".repeat(50))

        // Use Spring's classpath scanner to find @Day annotated classes
        val scanner = ClassPathScanningCandidateComponentProvider(false)
        scanner.addIncludeFilter(AnnotationTypeFilter(Day::class.java))

        val candidates: Set<BeanDefinition> = scanner.findCandidateComponents("com.nrsherr2.advent2025nrsherr2")

        // Sort by day number and execute
        val sortedDays = candidates
            .mapNotNull { beanDefinition ->
                try {
                    val clazz = Class.forName(beanDefinition.beanClassName).kotlin
                    val dayAnnotation = clazz.findAnnotation<Day>()
                    dayAnnotation?.let { Pair(it.day, clazz) }
                } catch (e: Exception) {
                    println("⚠️ Could not load class: ${beanDefinition.beanClassName}")
                    null
                }
            }
            .sortedBy { it.first }

        if (sortedDays.isEmpty()) {
            println("No @Day annotated classes found!")
            return
        }

        sortedDays.forEach { (dayNumber, clazz) ->
            runDay(dayNumber, clazz)
        }

        println("=".repeat(50))
        if (sortedDays.size == 25)
            println("🎅 All days completed! Merry Christmas! 🎅")
    }

    private fun runDay(dayNumber: Int, clazz: KClass<*>) {
        try {
            println("\n🗓️  Running Day $dayNumber...")

            // Create instance of the day class
            val instance = clazz.createInstance()

            // If it implements DaySolution, call solve()
            if (instance is DaySolution) {
                fun runStep(
                    name: String,
                    startMessage: String,
                    block: () -> Unit
                ) {
                    println(startMessage)
                    val start = System.nanoTime()
                    block()
                    val durationMs = (System.nanoTime() - start) / 1_000_000.0
                    println("   ⏱️  $name finished in ${"%.3f".format(durationMs)} ms")
                }

                runStep("Test Part 1",   "🔍✨ Starting Test Part 1…")   { instance.testPart1() }
                runStep("Test Part 2",   "🧪🔥 Starting Test Part 2…")   { instance.testPart2() }
                runStep("Solve Part 1",  "🎁🔓 Starting Solve Part 1…")  { instance.solvePart1() }
                runStep("Solve Part 2",  "🎄⚔️ Starting Solve Part 2…")  { instance.solvePart2() }

            } else {
                println("⚠️  Day $dayNumber class doesn't implement DaySolution interface")
            }

            println("✅ Day $dayNumber completed!")

        } catch (e: Exception) {
            println("❌ Error running Day $dayNumber: ${e.message}")
            e.printStackTrace()
        }
    }
}