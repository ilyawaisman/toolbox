package xyz.prpht.toolbox.strings

/**
 * Learns chars and create remapping of them to provided alphabet or sorted version of input.
 */

class CharLearner {
    private val learnt = LinkedHashSet<Char>()

    fun learn(c: Char) {
        learnt.add(c)
    }

    fun learn(s: String) = s.forEach { learn(it) }

    val learntSize
        get() = learnt.size

    fun remap(providedAlphabet: List<Char>? = null): (Char) -> Char {
        val alphabet =
                if (providedAlphabet == null)
                    learnt.toList().sorted()
                else
                    providedAlphabet.also {
                        require(providedAlphabet.size >= learntSize) { "Not enough letter in alphabet to remap: alphabet size is ${providedAlphabet.size}, learnt size is $learntSize" }
                        require(providedAlphabet.size == providedAlphabet.toSet().size) { "Alphabet $providedAlphabet has duplicate chars" }
                    }
        
        return doRemap(alphabet)
    }

    private fun doRemap(alphabet: List<Char>): (Char) -> Char {
        var i = 0
        val mapping = learnt.associateWith { alphabet[i++] }
        return { c: Char -> mapping[c] ?: error("Char '$c' was never learnt") }
    }
}
