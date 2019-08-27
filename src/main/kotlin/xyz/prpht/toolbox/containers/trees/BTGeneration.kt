package xyz.prpht.toolbox.containers.trees

import xyz.prpht.toolbox.random.checkChance
import kotlin.random.Random

/**
 * Generates random [BTNode] with decreasing probability of children.
 * Presence of each of children on level 1 (directly under the root) is [rate].
 * For level 2 it is [rate] squared and so on.
 * Function [nextValue] is used to generate values in nodes.
 */
fun <T> Random.nextBT(rate: Double, nextValue: Random.() -> T): BTNode<T> {
    require(rate < 1.0)

    fun nextNode(p: Double): BTNode<T> {
        val nextP = p * rate
        fun nextChild() = if (checkChance(p)) nextNode(nextP) else null
        return BTNode(
                nextValue(),
                nextChild(),
                nextChild()
        )
    }

    return nextNode(rate)
}

