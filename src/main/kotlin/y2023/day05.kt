package y2023

import resourceAsFile
import toLongList
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun LongRange.size() = max(0, last - first + 1)

    data class Rng(val start: Long, val size: Long, val offset: Long) {
        val range = start..<(start + size)

        fun splitBy(rm: Rng): List<Rng> {
            val first = range.first + offset
            val last = range.last + offset
            val mid = max(first, rm.range.first)..min(last, rm.range.last)
            if (mid.size() == 0L) return listOf(this)

            return listOf(
                Rng(first, mid.first - first, offset),
                Rng(mid.first, mid.size(), offset + rm.offset),
                Rng(mid.last + 1, last - mid.last, offset)
            ).filterNot { it.range.isEmpty() }
        }

        fun applyOffset() = if (offset == 0L) this else Rng(range.first + offset, size, 0)
    }

    fun parseRange(line: String) = line.toLongList().let { (dst, src, size) -> Rng(src, size, dst - src) }

    val input = resourceAsFile("y2023/day05-input-test.txt").readLines()

    val seeds = input.first().split(": ").last().toLongList()
    val mapping = input
        .drop(1)
        .filter { it.isNotBlank() }
        .fold(mutableListOf<MutableList<Rng>>()) { list, line ->
            list.also { if (line.contains("map:")) list.add(mutableListOf()) else list.last().add(parseRange(line)) }
        }

    val r1 = seeds.minOf { seed ->
        mapping.fold(seed) { value, ranges ->
            ranges.firstOrNull { r -> value in r.range }?.let { value + it.offset } ?: value
        }
    }

    println(r1)


    val seedRanges = seeds.chunked(2).map { (start, length) -> Rng(start, length, 0) }

    val r2 = mapping.fold(seedRanges) { currentRanges, nextRanges ->
        nextRanges.fold(currentRanges) { ranges, next ->
            ranges.flatMap { if (it.offset != 0L) listOf(it) else it.splitBy(next) }
                .filter { it.size > 0 }
        }.map { it.applyOffset() }
    }.minOf { it.start }

    println(r2)
}
