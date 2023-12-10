package y2023

import resourceAsFile
import toLongList

fun main() {
    val input = resourceAsFile("y2023/day06-input-test.txt").readLines()

    val input1 = input.map { it.split(":").last().toLongList() }
    val r1 = input1.first()
        .zip(input1.last())
        .map { (time, distance) -> (1..<time).map { it * (time - it) }.count { it > distance } }
        .reduce(Int::times)

    println(r1)


    val (time, distance) = input.map { it.split(":").last().replace(" ", "").toLong() }
    val r2 = (1..<time).map { it * (time - it) }.count { it > distance }

    println(r2)

}