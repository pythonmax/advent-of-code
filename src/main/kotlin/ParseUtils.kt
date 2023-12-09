
fun String.toLongList(delimiter:Char = ' ') = split(delimiter).filterNot { it.isEmpty() }.map { it.toLong() }.toList()
