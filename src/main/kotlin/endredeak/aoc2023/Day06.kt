package endredeak.aoc2023

import endredeak.aoc2023.lib.utils.productOf
import endredeak.aoc2023.lib.utils.transpose

fun main() {
    solve("Wait For It") {
        val input = lines.map { it.substringAfter(":") }

        part1(138915) {
            input
                .map { l -> l.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toLong() }
                }
                .transpose()
                .productOf { (t, r) -> countWays(t, r).toLong() }
        }

        part2(27340847) {
            input
                .map { it.replace(" ", "").toLong() }
                .let { (t, r) -> countWays(t, r) }
        }
    }
}

private fun countWays(t: Long, r: Long) = (1..<t).count { (t - it) * it > r }