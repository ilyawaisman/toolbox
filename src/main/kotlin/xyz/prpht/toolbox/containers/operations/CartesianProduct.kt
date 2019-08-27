package xyz.prpht.toolbox.containers.operations

infix fun <T, S> Iterable<T>.times(other: Iterable<S>): Iterable<Pair<T, S>> =
    flatMap { first ->
        other.map { second ->
            Pair(first, second)
        }
    }

fun crossJoin(vararg factors: Iterable<Any>): List<Array<Any>> =
    factors.fold(listOf(emptyArray())) { acc, next ->
        acc.flatMap { head ->
            next.map { tail ->
                head + tail
            }
        }
    }

