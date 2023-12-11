package endredeak.aoc2023

import kotlin.math.absoluteValue

enum class M { N, S, W, E }

operator fun Pair<Int, Int>.plus(move: M): Pair<Int, Int> = when (move) {
    M.N -> first - 1 to second
    M.S -> first + 1 to second
    M.W -> first to second - 1
    M.E -> first to second + 1
}

// first I did a BFS for part 1 and was lost on part 2
// then read Jakub's solution on actually counting the _moves_ which leads to complete the circle -> brilliant!
fun main() {
    solve("Pipe Maze") {
        operator fun List<String>.get(pos: Pair<Int, Int>): Char = getOrNull(pos.first)?.getOrNull(pos.second) ?: '.'

        val input = run {
            val start: Pair<Int, Int> = lines.indices.asSequence()
                .flatMap { row -> lines[row].indices.map { row to it } }
                .first { pos -> lines[pos] == 'S' }
            val firstMove = when {
                lines[start + M.N] in "7|F" -> M.N
                lines[start + M.E] in "7-J" -> M.E
                else -> M.S
            }
            generateSequence(start to firstMove) { (pos, move) ->
                val nextPos = pos + move
                if (nextPos != start) nextPos to when (lines[nextPos]) {
                    '|' -> if (move == M.N) M.N else M.S
                    '-' -> if (move == M.W) M.W else M.E
                    'L' -> if (move == M.S) M.E else M.N
                    'J' -> if (move == M.S) M.W else M.N
                    'F' -> if (move == M.N) M.E else M.S
                    '7' -> if (move == M.N) M.W else M.S
                    else -> error("$nextPos?")
                }
                else null
            }
                .map { it.second }
                .toList()
        }

        part1(6682) {
            input.count() / 2
        }

        part2(-1) {
            input.fold(0 to 0) { (sum, d), move ->
                when (move) {
                    M.N -> sum to d + 1
                    M.S -> sum to d - 1
                    M.W -> sum - d to d
                    M.E -> sum + d to d
                }
            }.first.absoluteValue - (input.size / 2) + 1
        }
    }
}