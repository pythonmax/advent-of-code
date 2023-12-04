package y2022

import XY
import resourceAsFile
import java.util.*
import kotlin.math.abs

fun main() {
    val base = 'a'
    var start = XY(0, 0)
    var end = XY(0, 0)
    val field = resourceAsFile("y2022/day12-input-test.txt").readLines().mapIndexed { y, l ->
        l.mapIndexed { x, c ->
            when (c) {
                'S' -> {
                    start = XY(x, y)
                    base - base
                }
                'E' -> {
                    end = XY(x, y)
                    'z' - base
                }
                else -> c - base
            }
        }
    }

    fun generatePath(currentPos: XY, cameFrom: Map<XY, XY>): List<XY> {
        val path = mutableListOf(currentPos)
        var current = currentPos
        while (cameFrom.containsKey(current)) {
            current = cameFrom.getValue(current)
            path.add(0, current)
        }
        return path.toList()
    }

    fun aStar(start: XY, end: XY, field: List<List<Int>>): List<XY>? {
        val cameFrom = mutableMapOf<XY, XY>()
        val estimatedTotalCost = mutableMapOf(start to start.distanceTo(end))
        val visited = mutableSetOf<XY>()
        val costFromStart = mutableMapOf(start to 0)
        val queue = PriorityQueue<XY> { pos1, pos2 -> estimatedTotalCost[pos1]!!.compareTo(estimatedTotalCost[pos2]!!) }
        queue.add(start)

        do {
            val pos = queue.poll()
            if (pos == end) {
                return generatePath(pos, cameFrom)
            }

            visited.add(pos)

            listOf(XY(pos.x + 1, pos.y), XY(pos.x - 1, pos.y), XY(pos.x, pos.y + 1), XY(pos.x, pos.y - 1))
                .filter { it.x >= 0 && it.y >= 0 && it.x < field[0].size && it.y < field.size }
                .filter { field[it.y][it.x] - field[pos.y][pos.x] <= 1 }
                .filterNot { visited.contains(it) }
                .forEach {
                    val dh = field[it.y][it.x] - field[pos.y][pos.x]
                    val score = costFromStart[pos]!! + (if (dh == 1) 1 else if (dh == 0) 2 else 2 + abs(dh))
                    if (score < costFromStart.getOrDefault(it, Int.MAX_VALUE)) {
                        cameFrom[it] = pos
                        costFromStart[it] = score
                        estimatedTotalCost[it] = score + it.distanceTo(end)
                        if (!queue.contains(it)) {
                            queue.add(it)
                        }
                    }
                }

        } while(queue.isNotEmpty())

        return null
    }

//    println(aStar(start, end, field))
    println(aStar(start, end, field)!!.size - 1)

    val r = field.asSequence().mapIndexed { y, row -> row.mapIndexed { x, v -> if (v == 0) XY(x, y) else null } }.flatten().filterNotNull()
        .map { aStar(it, end, field)?.let { it.size - 1 } }.filterNotNull().minOrNull()

    println(r)
}