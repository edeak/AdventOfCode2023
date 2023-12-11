package endredeak.aoc2023

import kotlin.math.abs

fun main() {
    solve("Cosmic Expansion") {
        fun Pair<Long, Long>.dist(other: Pair<Long, Long>) = abs(other.first - first) + abs(other.second - second)
        fun coordinate(c: Int, adds: List<Int>, factor: Long) = c.toLong() + adds.count { c > it } * (factor - 1)

        val input = lines

        val columns = input[0].indices.filter { i -> input.map { s -> s[i] }.all { it == '.' } }
        val rows = input.indices.filter { i -> input[i].all { it == '.' } }

        fun calculate(factor: Long) =
            input.flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, c ->
                    if (c == '#') {
                        coordinate(x, columns, factor) to coordinate(y, rows, factor)
                    } else null
                }
            }
                .let {
                    it
                        .flatMap { g -> it.map { n -> g to n } }
                        .sumOf { (g, n) -> g.dist(n) } / 2
                }

        part1(9693756) { calculate(2) }

        part2(717878258016) { calculate(1000000) }
    }
}