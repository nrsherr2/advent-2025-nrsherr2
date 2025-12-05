package com.nrsherr2.advent2025nrsherr2.utils

/**
 * Annotation to mark Advent of Code day solutions with their execution order.
 * Classes annotated with @Day will be executed in ascending order of their day number.
 *
 * @param day The day number (1-25) that determines execution order
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Day(val day: Int)
