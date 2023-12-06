package endredeak.aoc2023

import endredeak.aoc2023.lib.utils.productOf
import endredeak.aoc2023.lib.utils.transpose
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    solve("Wait For It") {
        val input = lines.map { it.substringAfter(":") }

        part1(138915) {
            input
                .map { it.split(" ").filter(String::isNotBlank).map(String::toLong) }
                .transpose()
                .productOf { solve(it[0], it[1]) }
        }

        part2(27340847) {
            input
                .map { it.filter(Char::isDigit).toLong() }
                .let { solve(it[0], it[1]) }
        }
    }
}

// solve x^2 - tx - d = 0
private fun solve(t: Long, r: Long) =
    (t / 2.0).let { b -> sqrt(b * b - r).let { d -> (ceil(b + d - 1) - floor(b - d + 1) + 1) } }.toLong()
    // (1..<t).count { (t - it) * it > r }.toLong() // brute-force solution (around 700ms)
