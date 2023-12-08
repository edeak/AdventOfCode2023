package endredeak.aoc2023

import endredeak.aoc2023.lib.utils.lcm

fun main() {
    solve("Haunted Wasteland") {
        val input =
            text
                .replace("(", "").replace(")", "")
                .split("\n\n")
                .let { (i, m) ->
                    i to m.split("\n")
                        .associate { it.split(" = ", ", ").let { (k, l, r) -> k to (l to r) } }
                }

        fun run(start: (String) -> Boolean, end: (String) -> Boolean) =
            input.let { (i, m) ->
                m.keys.filter(start).fold(1L) { acc, n ->
                    var s = 0
                    var c = n
                    while (end(c)) c = m[c]!!.let { if (i[s % i.length] == 'L') it.first else it.second }.also { s++ }
                    lcm(acc, s.toLong())
                }
            }

        part1(19637) { run({ it == "AAA" }, { !it.endsWith("ZZZ") }) }

        part2(8811050362409) { run({ it.endsWith("A") }, { !it.endsWith("Z") }) }
    }
}