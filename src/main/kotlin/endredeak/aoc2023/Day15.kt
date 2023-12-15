package endredeak.aoc2023

fun main() {
    solve("Lens Library") {
        val input = text.replace("\n", "").split(",")

        fun String.hash(): Int {
            var current = 0
            forEach { c ->
                current += c.code
                current *= 17
                current %= 256
            }
            return current
        }

        part1(505459) {
            input.sumOf { it.hash() }
        }

        data class Lens(val label: String, var focal: Int)

        part2(228508) {
            val boxes = (0..255).associateWith { mutableListOf<Lens>() }.toMutableMap()

            input.forEach { step ->
                if (step.contains("-")) {
                    step.replace("-", "")
                        .let { label ->
                            boxes[label.hash()]!!.removeIf { it.label == label }
                        }
                }

                if (step.contains("=")) {
                    step.split("=")
                        .let { (a, b) -> a to b.toInt() }
                        .let { (label, focal) ->
                            boxes[label.hash()]!!
                                .let { box ->
                                    box.singleOrNull { it.label == label }
                                        ?.apply { this.focal = focal }
                                        ?: box.add(Lens(label, focal))
                                }
                        }
                }
            }

            boxes.flatMap { (box, lenses) -> lenses.mapIndexed { i, l -> (box + 1) * (i + 1) * l.focal } }.sum()
        }
    }
}