package xyz.prpht.toolbox.containers.trees

/**
 * Converters for binary tree of chars to and from string notation defined as follows.
 * ```
 * node = <letter>
 * tree = node tree tree | _
 * ```
 * where `_` denotes empty tree; first `tree` is left subtree, second  is right.
 * E.g. trees
 * ```
 *        b     d
 *  a     'c   e'f
 * ```
 * are denoted as
 * ```
 * a__
 * b_c__
 * de__f__
 * ```
 * respectively.
 */

private const val emptyMarker = "_"

fun BTNode<Char>?.toStrRepr(): String = this?.let { "$value${left.toStrRepr()}${right.toStrRepr()}" } ?: emptyMarker

fun charBTFromStrRepr(str: String): BTNode<Char>? {
    var pos = 0
    fun parseBTInternal(): BTNode<Char>? {
        val value = str[pos++]
        return if (value == emptyMarker[0])
            null
        else
            BTNode(value, parseBTInternal(), parseBTInternal())
    }

    return parseBTInternal().also {
        check(pos == str.length) { "part of string left unused: ${str.substring(pos)}" }
    }
}

