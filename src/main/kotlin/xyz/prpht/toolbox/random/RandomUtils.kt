package xyz.prpht.toolbox.random

import xyz.prpht.toolbox.common.then
import kotlin.math.floor
import kotlin.random.Random

fun Random.checkChance(chance: Double) = chance > 0 && nextDouble() <= chance

fun Random.stochasticRound(value: Double): Int {
    val floor = floor(value)
    return floor.toInt() + checkChance(value - floor).then(1, 0)
}
