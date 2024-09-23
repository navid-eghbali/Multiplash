package navid.multiplash.common.ext

fun String.fromHexColorToLong() = "FF${removePrefix("#").lowercase()}".toLong(16)
