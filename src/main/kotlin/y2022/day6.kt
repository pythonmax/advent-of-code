package y2022

import resourceAsFile

fun main() {
    val all = resourceAsFile("y2022/day6-input.txt").readLines()

    fun marker(s: String, length: Int) = s.mapIndexed { i, c ->
        if (i >= length - 1 && s.substring(i + 1 - length, i + 1).toList().distinct().size == length) i + 1 else -1 }.first { it >= 0 }

//    all.map { marker(it, 4) }.forEach { println(it) }
    all.map { marker(it, 14) }.forEach { println(it) }
}