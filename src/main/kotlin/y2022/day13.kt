package y2022

import resourceAsFile
import java.util.*
import kotlin.collections.ArrayList

fun main() {

    fun parse(p: String): List<*> {
        val r = ArrayList<Any>()
        val parentListLink = IdentityHashMap<ArrayList<Any>, ArrayList<Any>>()

        var currentList = r
        var currentValue = ""

        p.forEach { when(it) {
            '[' -> {
                val newList = ArrayList<Any>()
                parentListLink[newList] = currentList
                currentList.add(newList)
                currentList = newList
            }
            ']' -> {
                if (currentValue.isNotEmpty()) {
                    currentList.add(currentValue.toInt())
                    currentValue = ""
                }
                currentList = parentListLink[currentList]!!
            }
            ',' -> {
                if (currentValue.isNotEmpty()) {
                    currentList.add(currentValue.toInt())
                    currentValue = ""
                }
            }
            else -> currentValue += it
        } }

//        println(r[0])
        return r[0] as List<*>
    }

    fun compare(p1: List<*>, p2: List<*>): Int {
        if (p1.isEmpty() && p2.isEmpty()) return 0
        if (p1.isEmpty()) return -1
        if (p2.isEmpty()) return 1

        for (i in 0 until minOf(p1.size, p2.size)) {
            val v1 = p1[i]
            val v2 = p2[i]

            if (v1 is Int && v2 is Int) {
                val r = v1.compareTo(v2)
                if (r != 0) return r else continue
            }

            if (v1 is List<*> && v2 is List<*>) {
                val r = compare(v1, v2)
                if (r != 0) return r else continue
            }

            if (v1 is List<*>) {
                val r = compare(v1, listOf(v2))
                if (r != 0) return r else continue
            }
            if (v2 is List<*>) {
                val r = compare(listOf(v1), v2)
                if (r != 0) return r else continue
            }
        }

        return p1.size.compareTo(p2.size)
    }

    val r1 = resourceAsFile("y2022/day13-input-test.txt").readText().split("\n\n")
        .mapIndexed { i, it -> Pair(i + 1, it.split("\n").let { compare(parse(it[0]), parse(it[1])) }) }
        .filter { it.second <= 0 }
        .sumOf { it.first }
    println(r1)

    val markers = listOf("[[2]]", "[[6]]")
    val r2 = (markers + resourceAsFile("y2022/day13-input.txt").readLines())
        .filter { it.isNotBlank() }
        .map { parse(it) }
        .sortedWith { a,b -> compare(a,b) }
        .mapIndexed { i, it -> Pair(i + 1, it) }
        .filter { markers.contains(it.second.toString()) }
        .map { it.first }
        .reduce { a, b -> a * b }
    println(r2)
}
