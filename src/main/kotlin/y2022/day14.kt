package y2022

import XY
import resourceAsFile
import kotlin.math.sign

fun main() {
    val walls = arrayListOf<XY>()

    resourceAsFile("y2022/day14-input.txt").forEachLine {
        val linePoints = it.split(" -> ").map { it.split(",").let { (x, y) -> XY(x.toInt(), y.toInt()) } }
        walls.add(linePoints.first())
        linePoints.drop(1).forEach { xy ->
            val move = (xy - walls.last()).let { XY(it.x.sign, it.y.sign) }
            while (walls.last() != xy) {
                walls.add(walls.last() + move)
            }
        }
    }

    val start = XY(500, 0)
    val moves = listOf(XY(0, 1), XY(-1, 1), XY(1, 1))
    val bottomY = 2 + walls.maxOf { it.y }

    val blocked = HashSet<XY>(walls)
    var sandCount = 0
    while (!blocked.contains(start)) {
        var p = start
        do {
            val move = moves.firstOrNull { (p + it).let { !blocked.contains(it) && it.y < bottomY } }
            if (move != null) {
                p += move
            } else {
                blocked.add(p)
                sandCount++
//                println("sand stopped at $p")
                break
            }
        } while (move != null)
    }

    println(sandCount)
}
