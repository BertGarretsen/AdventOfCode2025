package days

import Part
import java.io.BufferedReader

data class SimulationState(var position: Int, var splitCount: Int)

class Day7 {


    @Part("Part One")
    fun partOne(input: BufferedReader) {
        val world = parseGrid(input)


        val state = SimulationState(0, 0)
        simulateBeams(world, state)

        println("Total Splits: " + state.splitCount)
    }


    @Part("Part Two")
    fun partTwo(input: BufferedReader) {
        val world = parseGrid(input)

        val paths = countBeamPaths(world)
        println("Total Paths: $paths")
    }


    fun countBeamPaths(world: MutableList<CharArray>): Long {
        val startRow = 0
        val startCol = world[0].indexOfFirst { it == 'S' }

        val height = world.size
        val width = world[0].size

        val ways = Array(height) { LongArray(width) }

        // Bottom row
        for (c in 0 until width) {
            ways[height - 1][c] = 1L
        }

        for (r in height - 2 downTo 0) {
            for (c in 0 until width) {
                ways[r][c] = when (world[r][c]) {
                    '^' -> {
                        var total = 0L
                        total += ways[r + 1][c - 1]
                        total += ways[r + 1][c + 1]
                        total
                    }

                    else -> ways[r + 1][c]
                }
            }
        }

        for (longs in ways) {
            for (lng in longs) {
                print("\t|$lng|\t")
            }
            println()
        }

        return ways[startRow][startCol]
    }

    tailrec fun simulateBeams(world: MutableList<CharArray>, state: SimulationState) {
        // Stop if the next row is outside
        if (state.position + 1 >= world.size) return

        val previousLine = world[state.position]
        val currentLine = world[state.position + 1]

        val startIndex = previousLine.indexOfFirst { it == 'S' }
        if (startIndex != -1) {
            currentLine[startIndex] = '|'
        }

        for (index in previousLine.indices) {
            if (previousLine[index] != '|') continue

            if (currentLine[index] == '^') {
                state.splitCount++

                if (index > 0) currentLine[index - 1] = '|'
                if (index < currentLine.lastIndex) currentLine[index + 1] = '|'
            } else {
                currentLine[index] = '|'
            }
        }

        state.position++
        simulateBeams(world, state)
    }

    fun parseGrid(input: BufferedReader): MutableList<CharArray> {
        return input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .map { line -> line.toCharArray() }.toList()
        }.toMutableList()
    }
}