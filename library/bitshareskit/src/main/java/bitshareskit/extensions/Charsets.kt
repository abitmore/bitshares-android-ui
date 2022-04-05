package bitshareskit.extensions


internal fun String?.toByteArrayOrEmpty(): ByteArray = this?.toByteArray() ?: byteArrayOf()

internal fun ByteArray?.orEmpty(): ByteArray = this ?: byteArrayOf()

internal fun String.toUnicodeByteArray() = this.toByteArray(Charsets.UTF_8)

internal fun ByteArray.toUnicodeString() = this.toString(Charsets.UTF_8)

internal fun String.toHexByteArray() = ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }
internal fun String.toHexByteArray1() = chunked(2).map { it.toInt(16).toByte() }.toByteArray()

internal fun String?.toHexByteArrayOrEmpty() = this?.toHexByteArray() ?: byteArrayOf()

internal fun ByteArray.toHexString() = this.joinToString(separator = "") { String.format("%02x", (it.toInt() and 0xFF)) }
internal fun ByteArray.toHexString1() = joinToString("") { "%02x".format(it) }

internal const val EMPTY_SPACE = ""
internal const val BLANK_SPACE = " "
internal const val ZERO_WIDTH_BLANK_SPACE = "\u200b"
internal const val NEWLINE = "\n"

