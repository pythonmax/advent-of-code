package y2023

import resourceAsFile

fun main() {

    val labelsPart1 = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val labelsPart2 = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    fun cardLabelValues(card: String, labels: List<Char>) = card.map { labels.indexOf(it) }.joinToString("") { "%02d".format(it) }

    fun cardTypeValue(card: String) = card.groupingBy { it }.eachCount().values.sumOf { count -> (count - 1) * count }

    fun cardTypeValue2(card: String): Int {
        val eachCount = card.groupingBy { it }.eachCount()
        val jockerCount = eachCount['J'] ?: 0
        if (jockerCount == 4 || jockerCount == 5) return 4 * 5
        return eachCount.filterKeys { it != 'J' }.values.sortedDescending()
            .mapIndexed { idx, count -> (count - 1 + if (idx == 0) jockerCount else 0) * (count + if (idx == 0) jockerCount else 0) }
            .sum()
    }

    val input = resourceAsFile("y2023/day07-input.txt").readLines()
        .map { it.split(" ").let { (card, bid) -> card to bid.toLong() } }

    val r1 = input.map { (card, bid) -> Triple(cardTypeValue(card), cardLabelValues(card, labelsPart1), bid) }
        .sortedWith(compareBy({ it.first }, { it.second }))
        .mapIndexed { idx, (_, _, bid) -> (idx + 1) * bid }
        .sum()

    println(r1)


    val r2 = input.map { (card, bid) -> Triple(cardTypeValue2(card), cardLabelValues(card, labelsPart2), bid) }
        .sortedWith(compareBy({ it.first }, { it.second }))
        .mapIndexed { idx, (_, _, bid) -> (idx + 1) * bid }
        .sum()

    println(r2)

}
