package y2022

import resourceAsFile

fun main() {
    fun priority(it: Char): Int = if (it.isLowerCase()) it - 'a' + 1 else it - 'A' + 27

    val all = resourceAsFile("y2022/day3-input-test.txt").readLines().map { it.toList() }
    println(all.map { priority(it.subList(0, it.size/2).intersect(it.subList(it.size/2, it.size)).first()) }.sum())
    println(all.chunked(3).map { priority(it[0].intersect(it[1]).intersect(it[2]).first()) }.sum())
}