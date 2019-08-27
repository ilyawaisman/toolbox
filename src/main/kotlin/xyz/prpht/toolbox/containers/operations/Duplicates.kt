package xyz.prpht.toolbox.containers.operations

fun <T, S> Iterable<T>.dropDuplicatesBy(metric: (T) -> S): List<T> =
    LinkedHashMap<S, T>().also { map -> forEach { map.putIfAbsent(metric(it), it) } }.values.toList()

fun <K, V> Iterable<Pair<K, V>>.toMapCheckDuplicates(): Map<K, V> = HashMap<K, V>().also { map ->
    forEach {
        val replaced = map.putIfAbsent(it.first, it.second)
        check(replaced == null || replaced == it.second) {
            "When converting to map duplicate values detected: key = ${it.first}, values = $replaced, ${it.second}"
        }
    }
}
