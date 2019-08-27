package xyz.prpht.toolbox.containers.trees

import kotlin.math.max

class BTNode<T>(
        val value: T,
        val left: BTNode<T>?,
        val right: BTNode<T>?
)

fun <T, S> BTNode<T>.map(transform: (T) -> S): BTNode<S> = BTNode(transform(value), left?.map(transform), right?.map(transform))

fun <T> BTNode<T>.forEach(action: (T) -> Unit) {
    action(value)
    left?.forEach(action)
    right?.forEach(action)
}

fun BTNode<*>?.height(): Int = this?.let { 1 + max(left.height(), right.height()) } ?: 0
