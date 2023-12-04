package y2022

import resourceAsFile

fun main() {
    val inputFile = resourceAsFile("y2022/day2-input.txt")
    val lines = inputFile.readLines().map { it.split(" ").map { it[0] } }
    val r1 = lines.map { (abc, xyz) -> (xyz - 'X' + 1) + ((xyz - ('X' - 'A') + 4 - abc) % 3) * 3 }.sum()
    val r2 = lines.map { (abc, xyz) -> 1 + ((abc - 'A' + 1) + 1 + (xyz - 'X' + 1 - 4) % 3) % 3 + (xyz - 'X') * 3 }.sum()

    println(r1)
    println(r2)

    val S = "sldfkn"
    
}