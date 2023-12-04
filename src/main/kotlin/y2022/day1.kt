package y2022

import resourceAsFile

fun main() {
    val inputFile = resourceAsFile("y2022/day1-input-test.txt")
    val all = inputFile.readText().split("\n\n").map { it.split("\n").sumOf { it.toInt() } }.sortedByDescending { it }
    println(all[0])
    println(all.take(3).sum())
}
