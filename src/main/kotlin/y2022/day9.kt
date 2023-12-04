package y2022

import XY
import resourceAsFile
import java.lang.RuntimeException
import kotlin.math.abs

fun main() {
    fun solve(points: Int): Set<XY> {
        val body = (0 until points).map { XY(0, 0) }.toMutableList()
        val visited = mutableSetOf(body.last())
        resourceAsFile("y2022/day9-input.txt").forEachLine {
            val (dir, stepCount) = it.split(" ")
            val step = when (dir) {
                "L" -> XY(-1, 0)
                "R" -> XY(+1, 0)
                "U" -> XY(0, -1)
                "D" -> XY(0, +1)
                else -> throw RuntimeException("Unknown direction")
            }

            for (i in 0 until stepCount.toInt()) {
                body[0] += step
                for (j in 1 until body.size) {
                    val dx = abs(body[j - 1].x - body[j].x)
                    val dy = abs(body[j - 1].y - body[j].y)
                    if (dx + dy > 2) {
                        body[j] = body[j].moveBy(if (body[j - 1].x > body[j].x) +1 else -1, if (body[j - 1].y > body[j].y) +1 else -1)
                        if (j == body.size - 1) {
                            visited.add(body[j])
                        }
                    } else if (dx + dy > 1) {
                        if (dx > 1) {
                            body[j] = body[j].moveBy(if (body[j - 1].x > body[j].x) +1 else -1, 0)
                            if (j == body.size - 1) {
                                visited.add(body[j])
                            }
                        }
                        if (dy > 1) {
                            body[j] = body[j].moveBy(0, if (body[j - 1].y > body[j].y) +1 else -1)
                            if (j == body.size - 1) {
                                visited.add(body[j])
                            }
                        }
                    }
                }

//            println(body)
            }
        }
        return visited
    }

    println(solve(2).size)
    println(solve(10).size)
}