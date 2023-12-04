package y2022

import XY
import resourceAsFile
import kotlin.math.abs

fun main() {
    fun readCoords(file: String): List<List<XY>> {
        return resourceAsFile(file).readLines().map {
            it.replace("Sensor at x=", "").replace(" y=", "").replace(" closest beacon is at x=", "")
                .split(":").map { it.split(",").let { XY(it[0].toInt(), it[1].toInt()) } }
        }
    }

    fun solve1(file: String, targetY: Int) {
        val all = readCoords(file)

        val minX = all.minOf { minOf(it[0].x, it[1].x, it[0].x - it[0].distanceTo(it[1])) }
        val maxX = all.maxOf { maxOf(it[0].x, it[1].x, it[0].x + it[0].distanceTo(it[1])) }

        val r = (minX..maxX)
            .map { XY(it, targetY) }
            .filter { xy -> all.any { it[0].distanceTo(xy) <= it[0].distanceTo(it[1]) } }
            .filter { xy -> all.none { xy == it[1] } }
            .count()

        println(r)
    }

    fun distanceTo(x1: Int, y1: Int, x2: Int, y2: Int) = abs(x1 - x2) + abs(y1 - y2)


    fun solve2(file: String, min: Int, max: Int) {
        val all = readCoords(file)

        for (y in min..max) {
            var x = min
            while (x <= max) {
                val sAndB = all.firstOrNull { distanceTo(it[0].x, it[0].y, x, y) <= it[0].distanceTo(it[1]) }
                if (sAndB == null) {
                    println("x=$x, y=$y")
                    break
                }

                val (s, b) = sAndB
                if (x < s.x) {
                    x += abs(s.x - x)
                }
                x += 1 + s.distanceTo(b) - abs(s.y - y)
            }

//            if (y % 10 == 0) {
//                println(y)
//            }
        }
    }

//    solve1("y2022/day15-input-test.txt", 10)
//    solve1("y2022/day15-input.txt", 2000000)

//    solve2("y2022/day15-input-test.txt", 0, 20)
    val currentTimeMillis = System.currentTimeMillis()
    solve2("y2022/day15-input.txt", 0, 4000000)
    println(System.currentTimeMillis() - currentTimeMillis)
}