package endredeak.aoc2023

fun main() {
    solve("Mirage Maintenance") {
        val input = lines.map { l -> l.split(" ").map { it.toInt() } }

        fun List<Int>.extend(): Int = if (all { it == 0 }) 0 else (last() + zipWithNext { a, b -> b - a }.extend())

        part1(1995001648) { input.sumOf { it.extend() } }

        part2(988) { input.sumOf { it.reversed().extend() } }
    }
}