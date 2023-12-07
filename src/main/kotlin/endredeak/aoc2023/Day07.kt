package endredeak.aoc2023

val cardList = "AKQJT98765432".map { it.toString() }.reversed()
val jokerCardList = "AKQT98765432J".map { it.toString() }.reversed()
fun Char.r(jokers: Boolean = false) = (if (jokers) jokerCardList else cardList).indexOf(toString())

fun String.withJokers() =
    when {
        this.all { it == 'J' } -> "AAAAA"
        !this.contains("J") -> this
        else -> {
            this.replace("J",
                this.replace("J", "")
                    .groupBy { it }
                    .map { it.key to it.value.size }
                    .sortedWith(compareBy<Pair<Char, Int>> { it.second }.thenBy { it.first.r(true) }.reversed())
                    .first()
                    .first
                    .toString()
            )
        }
    }

fun main() {
    data class Hand(val cards: String, val bid: Long, val jokers: Boolean = false) : Comparable<Hand> {
        val type = (if (jokers) cards.withJokers() else cards).let { toUse ->
            listOf(
                toUse.toCharArray().distinct().size == 1, // 5
                toUse.any { c -> toUse.count { c == it } == 4 }, // 4
                toUse.groupBy { it }.let { g -> g.size == 2 && g.entries.any { it.value.size == 2 } }, // 3 2
                toUse.groupBy { it }.let { g -> g.size == 3 && g.entries.count { it.value.size == 3 } == 1 }, // 3
                toUse.groupBy { it }.let { g -> g.size == 3 && g.entries.count { it.value.size == 2 } == 2 }, // 2 2
                toUse.groupBy { it }.let { g -> g.size == 4 && g.count { it.value.size == 2 } == 1 }, // 2
                toUse.toCharArray().distinct().size == toUse.length, // 1
            )
                .reversed()
                .indexOfFirst { it }
        }

        override fun compareTo(other: Hand): Int =
            if (type == other.type) {
                // first index where chars are not equal
                cards.indices.first { cards[it] != other.cards[it] }.let {
                    this.cards[it].r(jokers).compareTo(other.cards[it].r(jokers))
                }
            } else {
                type.compareTo(other.type)
            }
    }

    solve("Camel Cards") {
        val input = lines.map { it.split(" ") }

        fun calculate(jokers: Boolean = false) =
            input
                .map { Hand(it.first(), it.last().toLong(), jokers) }
                .sortedWith(Hand::compareTo)
                .mapIndexed { i, h -> (i + 1) * h.bid }
                .sum()

        part1(253638586) { calculate() }

        part2(253253225) { calculate(true) }
    }
}