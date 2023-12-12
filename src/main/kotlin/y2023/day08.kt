package y2023

import resourceAsFile
import java.math.BigInteger

fun main() {

    val input = resourceAsFile("y2023/day08-input.txt").readLines()

    val keyPattern = "[0-9A-Z]+".toRegex()
    val mapping = input.drop(2)
        .fold(mutableMapOf<String, Pair<String, String>>()) { map, line ->
            map.also { m -> keyPattern.findAll(line).toList()
                .let { (mr1, mr2, mr3) -> m[mr1.value] = mr2.value to mr3.value } }
        }

    val dirs = input.first()

    fun next(node: String, index: Long) = mapping[node]!!.let { (l, r) ->
        if ('L' == (dirs[(index % dirs.length).toInt()])) l else r
    }

    fun hops(start: String, end: String) = generateSequence(Triple(start, mutableSetOf<String>(), 0L)) { (node, visited, index) ->
        if (index < 0 || end == node) null
        else {
            val nextIndex = if (visited.contains(node + (index % dirs.length))) -1L else index + 1
            visited.add(node + (index % dirs.length))
            Triple(next(node, index), visited, nextIndex)
        }
    }.last().third

    val r1 = hops("AAA", "ZZZ")
    println(r1)


    fun lcm(a: BigInteger, b: BigInteger) = a * (b / a.gcd(b))

    val endings = mapping.keys.filter { it.endsWith("Z") }

    val r2 = mapping.keys.filter { it.endsWith("A") }
        .flatMap { start -> endings.map { hops(start, it) } }
        .filter { it > 0 }
        .map { BigInteger.valueOf(it) }
        .reduce { a, b -> lcm(a, b) }

    println(r2)
}