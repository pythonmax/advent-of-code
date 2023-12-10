package y2023

import XY
import resourceAsFile

fun main() {
    fun parse(input: List<String>, pattern: String) =
        input.mapIndexed { idx, line ->
            Regex(pattern).findAll(line).map { it.value to XY(it.range.first, idx) }.toList()
        }.filterNot { it.isEmpty() }.flatten()

    fun isAdjacent(sym: XY, num: Pair<String, XY>) =
        sym.x in (num.second.x - 1)..(num.second.x + num.first.length)
                && sym.y in (num.second.y - 1)..(num.second.y + 1)

    val input = resourceAsFile("y2023/day03-input-test.txt").readLines()

    val symbols = parse(input, "[^.0-9]")
    val numbers = parse(input, "[0-9]+")

    val r1 = numbers.filter { num -> symbols.any { isAdjacent(it.second, num) } }.sumOf { it.first.toInt() }
    println(r1)

    val r2 = symbols.asSequence()
        .filter { it.first == "*" }
        .map { sym -> numbers.filter { isAdjacent(sym.second, it) } }
        .filter { it.size == 2 }
        .sumOf { it.fold(1L) { acc, num -> num.first.toInt() * acc } }
    println(r2)
}
