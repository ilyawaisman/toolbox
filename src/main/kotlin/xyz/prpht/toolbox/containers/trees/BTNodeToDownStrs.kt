package xyz.prpht.toolbox.containers.trees

import xyz.prpht.toolbox.strings.alignCenter
import xyz.prpht.toolbox.strings.prePost

/**
 * Creates pretty binary tree (see [BTNode]) strings representation.
 * Could be composed with [xyz.prpht.toolbox.strings.printlns] for example.
 */
fun <T> BTNode<T>?.toDownStrs(toStr: (T) -> String = { it.toString() }): List<String> {
    if (this == null)
        return emptyList()

    val btInfo = toInfo(toStr)

    val result = arrayListOf(btInfo.header(' ', ' '))
    var layer = listOf<LineChunk>(NodeChunk(btInfo))
    while (layer.any { it.hasChildren() }) {
        result.add(layer.joinToString("") { it.childrenStr() })
        layer = layer.flatMap { it.subChunks() }
    }
    return result
}

private data class NodeInfo(val str: String, val width: Int)

private fun BTNode<NodeInfo>?.width() = this?.value?.width ?: 0

private fun <T> BTNode<T>.toInfo(toStr: (T) -> String): BTNode<NodeInfo> {
    val leftInfo = left?.toInfo(toStr)
    val rightInfo = right?.toInfo(toStr)
    val str = toStr(value)
    val info = NodeInfo(str, leftInfo.width() + str.length + rightInfo.width())
    return BTNode(info, leftInfo, rightInfo)
}

private fun BTNode<NodeInfo>?.header(leftChar: Char, rightChar: Char) = this?.run { value.str.prePost(left.width(), leftChar, right.width(), rightChar) } ?: ""

private fun BTNode<*>?.armFiller() = if (this == null) ' ' else '-'

private interface LineChunk {
    fun subChunks(): List<LineChunk>
    fun hasChildren(): Boolean
    fun childrenStr(): String
}

private class SimpleChunk(private val len: Int) : LineChunk {
    override fun subChunks() = listOf(this)
    override fun hasChildren() = false
    override fun childrenStr() = " ".repeat(len)
}

private class NodeChunk(private val node: BTNode<NodeInfo>) : LineChunk {
    override fun subChunks() = listOfNotNull(node.left?.let(::NodeChunk), SimpleChunk(node.value.str.length), node.right?.let(::NodeChunk))
    override fun hasChildren() = node.left != null || node.right != null
    override fun childrenStr(): String {
        val downArm = if (hasChildren()) "'" else " "
        val downPart = downArm.alignCenter(node.value.str.length, node.left.armFiller(), node.right.armFiller())
        return node.left.header(' ', '-') + downPart + node.right.header('-', ' ')
    }
}
