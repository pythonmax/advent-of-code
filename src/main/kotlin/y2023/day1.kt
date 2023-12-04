package y2023

import resourceAsFile

fun main() {
    val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val digits = (1..9).map { it.toString() }
    val all = words + digits

    fun processLine(line: String): String {
        return listOf(line.findAnyOf(all)!!.second, line.findLastAnyOf(all)!!.second)
            .joinToString("") { if (it.length == 1) it else (1 + words.indexOf(it)).toString() }
    }

    val r = resourceAsFile("y2023/day1-input.txt").readLines()
        .map { processLine(it) }
        .sumOf { it.toInt() }
    println(r)
//    println(r.joinToString("\n"))
}
