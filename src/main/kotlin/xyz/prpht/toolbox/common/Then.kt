package xyz.prpht.toolbox.common

fun <T> Boolean.then(ifTrue: T, ifFalse: T) = if (this) ifTrue else ifFalse

inline fun <T> Boolean.then(ifTrue: () -> T) = if (this) ifTrue() else null

inline fun <T> Boolean.then(ifTrue: () -> T, ifFalse: () -> T) = if (this) ifTrue() else ifFalse()
