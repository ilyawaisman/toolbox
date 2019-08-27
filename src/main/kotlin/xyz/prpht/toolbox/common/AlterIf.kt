package xyz.prpht.toolbox.common

fun <T> T.alterIf(condition: Boolean, alter: T.() -> T) = if (condition) alter() else this

fun <T, N> T.alterIfNotNull(token: N?, alter: T.(N) -> T) = if (token != null) alter(token) else this
