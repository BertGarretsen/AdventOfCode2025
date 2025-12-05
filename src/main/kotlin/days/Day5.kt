package days

import Part
import java.io.BufferedReader

class Day5 {


    fun MutableList<LongRange>.isInAnyRange(id: Long): Boolean {
        return any { it.contains(id) }
    }

    @Part("Part One")
    fun partOne(input: BufferedReader) {
        val ranges: MutableList<LongRange> = mutableListOf()
        val ids: MutableList<Long> = mutableListOf();
        parseInput(input, ranges, ids)

        var freshCount = 0;
        for (id in ids) {
            if (ranges.isInAnyRange(id)) freshCount++;
        }

        println("Total fresh ingredients: $freshCount")
    }

    @Part("Part Two")
    fun partTwo(input: BufferedReader) {
        val ranges: MutableList<LongRange> = mutableListOf()
        parseInput(input, ranges, null)

        // Sort by first value
        val sortedRanges = ranges.sortedBy { it.first }
        var total = 0L

        var currentStart = sortedRanges[0].first
        var currentEnd = sortedRanges[0].last

        for (i in 1 until sortedRanges.size) {
            val nextRange = sortedRanges[i]

            if (nextRange.first > currentEnd) {
                total += (currentEnd - currentStart + 1)

                currentStart = nextRange.first
                currentEnd = nextRange.last
            } else {
                if (nextRange.last > currentEnd) {
                    currentEnd = nextRange.last
                }
            }
        }

        total += (currentEnd - currentStart + 1)

        println("Total Fresh Ingredients: $total")
    }

    fun parseInput(input: BufferedReader, ranges: MutableList<LongRange>?, ids: MutableList<Long>?) {
        input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .forEach { line ->
                    if (line.contains('-')) ranges?.add(line.trim().split('-').map { it.toLong() }
                        .let { LongRange(it[0], it[1]) })
                    else ids?.add(line.trim().toLong())
                }
        }

    }

}