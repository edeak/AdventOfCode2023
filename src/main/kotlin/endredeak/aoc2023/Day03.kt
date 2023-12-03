package endredeak.aoc2023

import endredeak.aoc2023.lib.utils.productOf
import kotlin.math.max
import kotlin.math.min

data class Symbol(val x: Int, val y: Int, val c: Char)
data class Number(val x: Int, val y: Int, var value: Long)

fun Char.isSymbol() = !this.isDigit() && this != '.'

fun createMap(lines: List<String>) = lines
    .let { grid ->
        val map = mutableMapOf<Symbol, MutableSet<Number>>()

        grid.indices.forEach { y ->
            var num = ""
            var offset = -1
            grid[0].indices.forEach { x ->
                val c = grid[y][x]

                if (c.isDigit()) {
                    if (num.isBlank()) {
                        offset = x
                    }
                    num += c
                }

                if (x == grid[0].lastIndex || !c.isDigit()) {
                    if (num.isNotBlank()) {
                        val number = Number(x, y, num.toLong())

                        (max(y - 1, 0)..min(grid.lastIndex, y + 1)).forEach { ny ->
                            (max(offset - 1, 0)..min(grid[0].lastIndex, x)).forEach { nx ->
                                val nc = grid[ny][nx]
                                if (nc.isSymbol()) {
                                    map.computeIfAbsent(Symbol(nx, ny, nc)) { mutableSetOf() }.add(number)
                                }
                            }
                        }

                        num = ""
                        offset = -1
                    }
                }
            }
        }

        map
    }

fun main() {
    solve("Gear Ratios") {
        val input = createMap(lines)

        part1(556367) {
            input.values
                .flatten()
                .sumOf { it.value }
        }

        part2(89471771) {
            input.filterKeys { it.c == '*' }
                .filterValues { it.size == 2 }
                .values
                .sumOf { it.productOf { n -> n.value } }
        }
    }
}