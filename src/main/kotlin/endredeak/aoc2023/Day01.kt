package endredeak.aoc2023

fun main() {
    solve("Trebuchet?!") {
        val digits = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        fun String.getDigits(convertText: Boolean): Int {
            var tmp = this

            if (convertText) {
                digits.forEachIndexed { i, v -> tmp = tmp.replace(v, "$v$i$v") }
            }

            return "${tmp.firstOrNull { it.isDigit() } }${tmp.lastOrNull { it.isDigit()} }"
                .replace("null", "0")
                .toInt()
        }

        val input = text
            .split("\n")


        part1(54159) {
            input.sumOf { it.getDigits(false) }
        }

        part2(53866) {
            input.sumOf { it.getDigits(true) }
        }
    }
}