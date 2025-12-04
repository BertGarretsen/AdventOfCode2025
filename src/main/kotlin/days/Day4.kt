package days

import Part
import java.io.BufferedReader

class Day4 {


    @Part("Part 1")
    fun partOne(input: BufferedReader) {
        val rolls = parse(input).findAvailableRolls(4).size
        println("Available rolls: $rolls")
    }


    @Part("Part 2")
    fun partTwo(input: BufferedReader) {
        val grid = parse(input)

        var indexes = grid.findAvailableRolls(4)
        var rolls = indexes.size
        while (indexes.isNotEmpty()) {
            indexes.forEach { grid[it.first][it.second] = false }
            indexes = grid.findAvailableRolls(4)
            rolls += indexes.size
        }

        println("Available rolls: $rolls")
    }

    fun parse(input: BufferedReader): MutableList<MutableList<Boolean>> {
        return input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .map { line ->
                    line.map { char -> char == '@' }.toMutableList()
                }.toMutableList()
        }
    }

    fun MutableList<MutableList<Boolean>>.findAvailableRolls(limit: Int): List<Pair<Int, Int>> {
        val indexes = mutableListOf<Pair<Int, Int>>()
        this.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, _ ->
                if (this[rowIndex][colIndex] && this.countNeighbours(rowIndex, colIndex) < limit) {
                    indexes.add(Pair(rowIndex, colIndex))
                }
            }
        }
        return indexes;
    }

    fun List<List<Boolean>>.countNeighbours(rowIndex: Int, colIndex: Int): Int {
        var count = 0;
        for (row in -1..1) {
            for (column in -1..1) {
                val cx = rowIndex + row
                val cy = colIndex + column

                // check for out of bounds
                if (cx < 0 || cx >= this.size || cy < 0 || cy >= this[0].size) continue
                // skip self
                if (cx == rowIndex && cy == colIndex) continue
                if (this[cx][cy]) count++;
            }
        }
        return count
    }
}