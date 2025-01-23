package navid.multiplash.common.ext

fun Long.withDecimalSeparator(): String = this
    .toString()
    .reversed()
    .chunked(3)
    .joinToString(",")
    .reversed()
