package navid.multiplash.common.ext

fun Int.withDecimalSeparator(): String = this
    .toString()
    .reversed()
    .chunked(3)
    .joinToString(",")
    .reversed()
