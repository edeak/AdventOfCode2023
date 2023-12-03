package endredeak.aoc2023

fun main() {
    data class GameSet(val r: Long, val g: Long, val b: Long)
    data class Game(val id: Long, val sets: List<GameSet>) {
        fun isPossible(r: Long, g: Long, b: Long) = sets.all { s -> s.r <= r && s.g <= g && s.b <= b }
        fun fewestCubes() = Triple(sets.maxOf { it.r }, sets.maxOf { it.g }, sets.maxOf { it.b })
    }

    solve("Cube Conundrum") {
        val input = lines
            .map { it.replace(": ", ":")
                .replace(", ", ",")
                .replace("; ", ";")}
            .map { line ->
                line.split(":").let { (f, s) ->
                    val gameId = f.split(" ")[1].toLong()

                    val sets = s.split(";").map { set ->
                        var r = 0L
                        var g = 0L
                        var b = 0L

                        set.split(",").forEach { cubes ->
                            cubes.split(" ").let { (n, c) ->
                                n.toLong().let {
                                    when (c) {
                                        "red" -> { r = it }
                                        "green" -> { g = it }
                                        "blue" -> { b = it }
                                        else -> error("waat")
                                    }
                                }
                            }
                        }

                        GameSet(r, g, b)
                    }

                    Game(gameId, sets)
                }
            }

        part1(3035) {
            input.filter { it.isPossible(12, 13, 14) }.sumOf { it.id }
        }

        part2(66027) {
            input.map { it.fewestCubes() }.sumOf { it.first * it.second * it.third }
        }
    }
}