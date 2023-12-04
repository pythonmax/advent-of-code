package y2022

import resourceAsFile

fun main() {
    val (state, moves) = resourceAsFile("y2022/day5-input.txt")
        .readText().split("\n\n").map { it.split("\n") }

    val matrix = state.dropLast(1)
        .map { it.mapIndexed { j, c -> c to (j + 1) / 4 }.filter { it.first.isLetter() } }
        .flatten()
        .groupBy { it.second }.entries.sortedBy { it.key }
        .map { it.value.map { it.first }.reversed() }

    fun move(m: List<List<Char>>, src: Int, dst: Int, count: Int, reverse: Boolean) = List(m.size) {
        when (it) {
            dst -> m[dst] + m[src].takeLast(count).let { if (reverse) it.reversed() else it }
            src -> m[src].take(m[src].size - count)
            else -> m[it]
        }
    }

    fun apply(moves: List<String>, reverse: Boolean) = moves
        .map { it.split(" ").mapNotNull { it.toIntOrNull() } }
        .fold(matrix) { m, (count, src, dst) -> move(m, src - 1, dst - 1, count, reverse) }
        .map { it.last() }.joinToString("")

    println(apply(moves, true))
    println(apply(moves, false))

}