package xyz.prpht.toolbox.strings

class OutputFormatUtils(private val width: Int = 0x40) {
    private val cachedHLines = HashMap<Char, String>()

    fun hLine(c: Char) = cachedHLines.getOrPut(c) { StringBuilder().apply { repeat(width) { append(c) } }.toString() }
}
