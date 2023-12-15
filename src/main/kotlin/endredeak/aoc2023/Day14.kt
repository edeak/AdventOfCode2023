package endredeak.aoc2023

fun main() {
    solve("Parabolic Reflector Dish") {
        val input = lines.map { it.toCharArray() }

        fun List<CharArray>.copy() = this.map { it.toMutableList().toCharArray() }

        fun List<CharArray>.tilt(dir: String): List<CharArray> {
            val c = this.copy()
            var prev = c.copy()

            while (true) {
                when (dir) {
                    "N" -> (c.lastIndex downTo 1).forEach { y ->
                        c.first().indices.forEach { x ->
                            if (c[y][x] == 'O') {
                                if (c[y - 1][x] == '.') {
                                    c[y - 1][x] = 'O'
                                    c[y][x] = '.'
                                }
                            }
                        }
                    }

                    "W" -> {
                        c.indices.forEach { y ->
                            (c.first().lastIndex downTo 1).forEach { x ->
                                if (c[y][x] == 'O') {
                                    if (c[y][x - 1] == '.') {
                                        c[y][x - 1] = 'O'
                                        c[y][x] = '.'
                                    }
                                }
                            }
                        }
                    }

                    "S" -> {
                        (0..<c.lastIndex).forEach { y ->
                            c.first().indices.forEach { x ->
                                if (c[y][x] == 'O') {
                                    if (c[y + 1][x] == '.') {
                                        c[y + 1][x] = 'O'
                                        c[y][x] = '.'
                                    }
                                }
                            }
                        }
                    }

                    "E" -> {
                        c.indices.forEach { y ->
                            (0..<c.first().lastIndex).forEach { x ->
                                if (c[y][x] == 'O') {
                                    if (c[y][x + 1] == '.') {
                                        c[y][x + 1] = 'O'
                                        c[y][x] = '.'
                                    }
                                }
                            }
                        }
                    }
                }

                if (indices.all { prev[it].contentEquals(c[it]) }) {
                    break
                }
                prev = c.copy()
            }

            return c.copy()
        }

        fun List<CharArray>.calc() = first().indices.sumOf { x ->
            this.joinToString("") { "${it[x]}" }
                .let { col ->
                    col.indices.fold(0L) { acc, i ->
                        if (col[i] == 'O') acc + lastIndex - i + 1 else acc
                    }
                }
        }

        fun List<CharArray>.repeatedTilt(limit: Int): List<CharArray> {
            val seen = mutableMapOf<List<String>, Int>()
            var current = this
            var i = 0
            var length = 0

            while (i < limit) {
                val key = current.map { it.concatToString() }
                if (key in seen) {
                    length = i - seen[key]!!
                    break
                }
                seen[key] = i

                current = current.tilt("N").tilt("W").tilt("S").tilt("E")
                i++
            }

            if (length > 0) {
                val remainingCycles = (limit - i) % length
                for (c in 0 until remainingCycles) {
                    current = current.tilt("N").tilt("W").tilt("S").tilt("E")
                }
            }

            return current
        }

        part1(107142) {
            input.tilt("N").calc()
        }

        part2(104815) {
            input.repeatedTilt(1_000_000_000).calc()
        }
    }
}
