package y2022

import resourceAsFile

fun main() {
    val all = resourceAsFile("y2022/day4-input.txt").readLines()
        .map { it.split(",").map { it.split("-").let { it[0].toInt().rangeTo(it[1].toInt()).toList() } } }
    println(all.map { if (setOf(it[0].size, it[1].size).contains(it[0].intersect(it[1]).size)) 1 else 0 }.sum())
    println(all.map { if (it[0].intersect(it[1]).isEmpty()) 0 else 1 }.sum())
}