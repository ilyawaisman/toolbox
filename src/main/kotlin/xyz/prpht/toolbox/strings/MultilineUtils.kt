package xyz.prpht.toolbox.strings

/**
 * Just print a series of lines one after another.
 */
fun printlns(lns: Iterable<CharSequence>) = lns.forEach { println(it) }

/**
 * Given several blocks of strings (or some objects convertible to blocks of strings).
 * This function allows to concatenate these blocks horizontally like [joinToString] does for simple strings.
 * [separator], [prefix] and [postfix] are used in every result strings.
 * E.g., you want to concatenate these two blocks:
 * ```
 * ab
 * abc
 * ```
 * and
 * ```
 * xyz
 * xy
 * ```
 * You can call `blocks.joinToStringsH(separator = "||", prefix = "(", postfix = ")")` to obtain following result:
 * ```
 * (ab ||xyz)
 * (abc||xy )
 * ```
 * Note that block are aligned horizontally in columns.
 * The height of resulting block will be the maximum height of elements.
 * You can control vertical alignment of elements of smaller height with parameter [vAlign] which can take values: [VAlign.Top], [VAlign.Center], [VAlign.Bottom].
 */
fun <T> Iterable<T>.joinToStringsH(separator: CharSequence = " ", prefix: CharSequence = "", postfix: CharSequence = "", vAlign: VAlign = VAlign.Bottom, transform: ((T) -> List<CharSequence>)? = null): List<String> {
    val preblocks = map { it.makeStrings(transform) }
    val height = preblocks.map { it.size }.max() ?: return emptyList()
    val blocks = preblocks.map { strings ->
        val width = strings.map { it.length }.max() ?: 0
        strings.growTo(height, vAlign).map { it.growTo(width) }
    }

    return (0 until height).map { layerIdx -> blocks.joinToString(separator, prefix, postfix) { it[layerIdx] } }
}

private fun List<CharSequence>.growTo(newSize: Int, vAlign: VAlign): List<CharSequence> {
    if (newSize == size)
        return this

    val growNum = newSize - size
    val topGrowNum = when (vAlign) {
        VAlign.Top -> 0
        VAlign.Center -> growNum / 2
        VAlign.Bottom -> growNum
    }
    val bottomGrowNum = growNum - topGrowNum
    return (0 until topGrowNum).map { "" } + this + (0 until bottomGrowNum).map { "" }
}

private fun CharSequence.growTo(newLength: Int) = toString() + " ".repeat(newLength - length)

/**
 * Given several blocks of strings (or some objects convertible to blocks of strings).
 * This function allows to join these blocks horizontally like [joinToString] does for simple strings.
 * [separator], [prefix] and [postfix] are treated as vertical block here also.
 * E.g., you want to concatenate these two blocks:
 * ```
 * ab
 * abc
 * ```
 * and
 * ```
 * xyz
 * xy
 * ```
 * You can call `blocks.joinToStringsH(separator = listOf("---"), prefix = listOf("==="), postfix = listOf("###"))` to obtain following result:
 * ```
 * ===
 * ab
 * abc
 * ---
 * xyz
 * xy
 * ###
 * ```
 */
fun <T> Iterable<T>.joinToStringsV(separator: List<CharSequence> = listOf(""), prefix: List<CharSequence> = emptyList(), postfix: List<CharSequence> = emptyList(), transform: ((T) -> List<CharSequence>)? = null): List<CharSequence> {
    val result = ArrayList(prefix)
    var isFirst = true
    forEachIndexed { idx, element ->
        if (isFirst)
            isFirst = false
        else
            result.addAll(separator)
        result.addAll(element.makeStrings(transform))
    }
    result.addAll(postfix)
    return result
}

private fun <T> T.makeStrings(transform: ((T) -> List<CharSequence>)?) =
        when {
            transform != null -> transform(this)
            this is Iterable<*> -> this.map { it.toString() }
            else -> listOf(toString())
        }
