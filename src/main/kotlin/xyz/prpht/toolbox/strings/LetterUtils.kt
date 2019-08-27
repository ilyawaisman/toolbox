package xyz.prpht.toolbox.strings

import kotlin.random.Random

/**
 * Random generation of letters and words.
 */

fun Random.nextLetter() = 'a' + nextInt(26)

fun Random.nextWord(lenRange: IntRange) = (0 until lenRange.random(this)).map { nextLetter() }.joinToString("")
