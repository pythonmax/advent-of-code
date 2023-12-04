package y2023

import resourceAsFile

fun main() {
    val games = resourceAsFile("y2023/day2-input-test.txt").readLines()
        .map { game -> game.split(": ")[1].split("; ") }
        .map { sets -> sets.map { set -> set.split(", ").map { it.split(" ").let { (i, c) -> c to i.toInt() }}}}

    val max = mapOf("red" to 12, "green" to 13, "blue" to 14)
    val r1 = games
        .mapIndexed { num, sets -> if (sets.all { set -> set.all { it.second <= max[it.first]!! } }) num + 1 else 0 }
        .sum()
    println(r1)

    val r2 = games.sumOf { sets ->
        sets.flatten()
            .groupingBy { it.first }
            .fold(listOf<Int>()) { acc, item -> acc + item.second }
            .values.fold(1L) { acc, it -> acc * it.max() }
    }
    println(r2)
}
