package y2022

import resourceAsFile
import kotlin.math.abs

fun main() {
    resourceAsFile("y2022/day10-input.txt").useLines { lines ->
        val all = lines.map { when {
            it.startsWith("noop") -> listOf(0 to it)
            it.startsWith("addx") -> listOf(0 to it, it.split(" ").last().toInt() to it)
            else -> listOf(0 to "")
        } }
            .flatten()
            .fold(listOf(1 to "")) { r,v -> r + listOf((r.last().first + v.first) to v.second) }

        val r1 = all.mapIndexed { i, v -> (i + 1) * v.first }
            .filterIndexed { i, _ -> i >= 19 && (i - 19) % 40 == 0 }
            .sum()
        println(r1)

        all.forEachIndexed { i, v ->
            val pos = i % 40
            print(if (abs(pos - v.first) <= 1) "#" else ".")
            if (pos == 39) {
                println()
            }
        }
    }
}