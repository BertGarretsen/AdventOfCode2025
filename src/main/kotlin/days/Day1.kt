package days

import Part
import java.io.BufferedReader

class Day1 {


    fun Int.rotate(steps: Int): Int = Math.floorMod(this + steps, 100)

    fun countHits(start: Int, steps: Int): Int {
        if (steps == 0) return 0
        return if (steps > 0) {
            val first = if (start == 0) 100 else 100 - start
            if (steps < first) 0
            else 1 + (steps - first) / 100
        } else {
            val dist = -steps
            val first = if (start == 0) 100 else start
            if (dist < first) 0
            else 1 + (dist - first) / 100
        }
    }

    @Part("Part 1")
    fun partOne(input: BufferedReader) {
        var pos = 50
        var hits = 0

        input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .forEach { line ->
                    val trimmed = line.trim()
                    val dir = trimmed[0].uppercaseChar()
                    val amount = trimmed.substring(1).trim().toInt()

                    val steps = when (dir) {
                        'R' -> amount
                        'L' -> -amount
                        else -> error("Unknown direction: $dir in line: $line")
                    }
                    pos = pos.rotate(steps)
                    if (pos == 0) hits++
                }
        }

        println("Hits: $hits")
    }


    @Part("Part 2")
    fun partTwo(input: BufferedReader) {
        var pos = 50
        var hits = 0

        input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .forEach { line ->
                    val trimmed = line.trim()
                    val dir = trimmed[0].uppercaseChar()
                    val amount = trimmed.substring(1).trim().toInt()

                    val steps = when (dir) {
                        'R' -> amount
                        'L' -> -amount
                        else -> error("Unknown direction: $dir in line: $line")
                    }

                    val oldPos = pos
                    hits += countHits(oldPos, steps)
                    pos = pos.rotate(steps)
                }
        }

        println("Hits: $hits")
    }
}