package y2022

import resourceAsFile
import java.lang.Integer.max
import kotlin.math.abs

fun main() {
    val all = resourceAsFile("y2022/day8-input.txt").readLines()
        .map { it.map { it.digitToInt() } }.toList()

    val rows = all.size
    val cols = all[0].size

    var r = 0
    for (y in 1 until rows-1) {
        for (x in 1 until cols-1) {
            val element = all[y][x]
            val x0 = abs(x - ((0 until x).map { all[y][it] to it }.reversed().firstOrNull { element <= it.first }?.second ?: 0))
            val x1 = abs(x - ((x+1 until cols).map { all[y][it] to it }.firstOrNull { element <= it.first }?.second ?: cols-1))
            val y0 = abs(y - ((0 until y).map { all[it][x] to it }.reversed().firstOrNull { element <= it.first }?.second ?: 0))
            val y1 = abs(y - ((y+1 until rows).map { all[it][x] to it }.firstOrNull { element <= it.first }?.second ?: rows-1))
            val m = x0*x1*y0*y1
//            println("x=$x, y=$y, m=$m")
            r = max(r, m)
        }
    }

    println(r)
}