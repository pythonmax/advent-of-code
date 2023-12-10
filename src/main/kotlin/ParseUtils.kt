
fun String.toLongList(separatorPattern: String = "\\s+") = split(separatorPattern.toRegex())
    .filterNot { it.isEmpty() }
    .map { it.toLong() }
    .toList()
