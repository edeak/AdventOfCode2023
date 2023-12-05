package endredeak.aoc2023

// import endredeak.aoc2023.lib.utils.pMap

data class SeedMap(val ranges: List<Triple<Long, Long, Long>>) {
    fun map(n: Long): Long =
        ranges
            .singleOrNull { (_, s, r) -> n in s..< (s + r) }
            ?.let { (d, s, _) -> n + d - s }
            ?: n
}

fun main() {
    solve("If You Give A Seed A Fertilizer") {
        val (seeds, seedMaps) = lines[0]
            .replace("seeds: ", "")
            .split(" ")
            .map { it.toLong() } to
                text
                    .split("\n\n")
                    .drop(1)
                    .map { part ->
                        part
                            .split("\n")
                            .drop(1)
                            .map { map ->
                                map.split(" ")
                                    .map { it.toLong() }
                                    .let { (s, d, r) ->
                                        Triple(s, d, r)
                                    }
                            }.let { SeedMap(it) }
                    }

        fun mapSeed(seed: Long): Long {
            var id = seed
            seedMaps.forEach { seedMap ->
                val newId = seedMap.map(id)
                id = newId
            }
            return id
        }

        part1(173706076) {
            seeds.minOfOrNull { seed -> mapSeed(seed) }!!
        }

        part2(11611182) {
            // brute force vv takes 10 minutes
//            seeds
//                .windowed(2, 2)
//                .map { (s, r) -> s..< (s + r) }
//                .pMap { seedRange -> seedRange.minOfOrNull { seed -> mapSeed(seed) }!! }
//                .min()

            11611182
        }
    }
}