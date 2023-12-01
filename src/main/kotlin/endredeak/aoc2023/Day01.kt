package endredeak.aoc2023

fun main() {
    solve("Trebuchet?!") {
        val digitMap = mapOf(
            "zero" to 0,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        fun String.getDigits(convertText: Boolean): Int {
            var digitized = this

            if (convertText) {
                digitMap.forEach {(k, v) ->
                    digitized = digitized.replace(k, "$k$v$k")
                }
            }

            return "${digitized.firstOrNull { it.isDigit() } ?: 0}${digitized.lastOrNull { it.isDigit()} ?: 0}".toInt()
        }

        val input = text
            .split("\n")


        part1(54159) {
            input.sumOf { it.getDigits(false) }
        }

        part2(-1) {
            input.sumOf { it.getDigits(true) }
        }
    }
}