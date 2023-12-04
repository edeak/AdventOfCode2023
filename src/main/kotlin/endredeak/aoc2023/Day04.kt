package endredeak.aoc2023

import kotlin.math.pow

fun main() {
    solve("Scratchcards") {
        val input = lines
            .map { line ->
                line.substringAfter(":")
                    .trimStart()
                    .replace("  ", " ")
                    .replace(" | ", "|")
                    .split("|").let { (c, n) ->
                        val ws = c.split(" ").map { it.toInt() }
                        val ns = n.split(" ").map { it.toInt() }

                        ws.count { it in ns }
                    }
            }

        part1(17803) { input.filter { it > 0 }.sumOf { 2.0.pow(it - 1).toInt() } }

        part2(5554894) {
            val counts = input.map { 1 }.toMutableList()

            input.indices.forEach { c -> (c + 1..<c + 1 + input[c]).forEach { w -> counts[w] += counts[c] } }

            counts.sum()
        }
    }
}