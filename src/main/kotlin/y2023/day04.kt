package y2023

import resourceAsFile

fun main() {

    fun toSet(numbers: String) = numbers.split(" ")
        .filterNot { it.isEmpty() }
        .map { it.toInt() }
        .toSet()

    val input = resourceAsFile("y2023/day04-input.txt").readLines()
        .map { row -> row.split(": ").last().split(" | ").map(::toSet) }
        .map { (winners, actual) -> actual.intersect(winners).size }

    val r1 = input
        .filter { it > 0 }
        .sumOf { 1 shl (it - 1) }
    println(r1)

    val size = input.count()

    val r2 = input.foldIndexed(List(size) { 1 }) { idx, list, wins ->
        list.zip(List(size) { if (it in (idx + 1)..(idx + wins)) list[idx] else 0 }, Int::plus)
    }.sum()
    println(r2)
    
}
