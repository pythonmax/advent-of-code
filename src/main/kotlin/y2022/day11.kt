package y2022

import resourceAsFile

data class Monkey(
    val items: MutableList<Long>,
    val operation: Pair<String, String>,
    val passTo: Triple<Int, Int, Int>,
    var inspect: Long = 0
)

fun main() {
    fun solve(rounds: Int, relief: Int) {
        val monkeys = resourceAsFile("y2022/day11-input.txt").readText()
            .split("\n\n")
            .map { moneyLines ->
                moneyLines.split("\n").drop(1).let { (items, op, test, testOk, testFail) ->
                    Monkey(
                        items.substring("  Starting items: ".length).split(", ").map { it.toLong() }.toMutableList(),
                        op.substring("  Operation: new = old ".length).split(" ").let { it[0] to it[1] },
                        Triple(
                            test.substring("  Test: divisible by ".length).toInt(),
                            testOk.substring("    If true: throw to monkey ".length).toInt(),
                            testFail.substring("    If false: throw to monkey ".length).toInt()
                        )
                    )
                }
            }

        val magic = monkeys.map { it.passTo.first }.reduce { a, b -> a * b }
        repeat(rounds) { round ->
            for (m in monkeys) {
                for (item in m.items) {
                    m.inspect++
                    val operand = if (m.operation.second == "old") item else m.operation.second.toLong()
                    val value = when (m.operation.first) {
                        "*" -> item * operand
                        "+" -> item + operand
                        else -> item
                    } % magic / relief
                    val nextMonkey = if (value % m.passTo.first == 0L) m.passTo.second else m.passTo.third
                    monkeys[nextMonkey].items.add(value)
                }
                m.items.clear()
            }
//            if (round % 1000 == 0) {
//                monkeys.map { it.items }.forEach {
//                    println("$round: $it")
//                }
//            }
        }
        println(monkeys.map { it.inspect }.sortedBy { -it }.take(2).reduce { a, b -> a * b })
    }

    solve(20, 3)
    solve(10000, 1)
}