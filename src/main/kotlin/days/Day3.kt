package days

import Part
import java.io.BufferedReader


class Day3 {

    fun resolveJoltage(bank: CharArray, cellCount: Int): Long {
        val n = bank.size
        val result = StringBuilder(cellCount)
        var startIndex = 0

        for (pos in 0 until cellCount) {
            val maxIndex = n - (cellCount - pos)

            var bestIdx = startIndex
            var bestDigit = bank[bestIdx]

            var i = startIndex + 1
            while (i <= maxIndex) {
                val d = bank[i]
                if (d > bestDigit) {
                    bestDigit = d
                    bestIdx = i
                    if (bestDigit == '9') break
                }
                i++
            }

            result.append(bestDigit)
            startIndex = bestIdx + 1
        }

        return result.toString().toLong()
    }

    fun getTotalJoltage(input: BufferedReader, cellCount: Int): Long {
        var totalJoltage = 0L
        input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .forEach { line ->
                    totalJoltage += resolveJoltage(line.trim().toCharArray(), cellCount)
                }
        }
        return totalJoltage
    }


    @Part("Part 1")
    fun partOne(input: BufferedReader) {
        print("Total Joltage: ${getTotalJoltage(input, 2)}")
    }


    @Part("Part 2")
    fun partTwo(input: BufferedReader) {
        print("Total Joltage: ${getTotalJoltage(input, 12)}")
    }

}