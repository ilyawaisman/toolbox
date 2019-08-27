package xyz.prpht.toolbox.strings

fun String.prePost(prefixLen: Int, prefixChar: Char, postfixLen: Int, postfixChar: Char) =
        StringBuilder().let { sb ->
            repeat(prefixLen) { sb.append(prefixChar) }
            sb.append(this)
            repeat(postfixLen) { sb.append(postfixChar) }
            sb.toString()
        }

fun String.alignCenter(width: Int, leftFiller: Char = ' ', rightFiller: Char = leftFiller): String {
    require(length <= width)
    val exceed = width - length
    val prefixLen = exceed / 2
    val postfixLen = exceed - prefixLen
    return prePost(prefixLen, leftFiller, postfixLen, rightFiller)
}

