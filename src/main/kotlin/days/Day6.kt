package days

import Part
import java.io.BufferedReader

class Day6 {


    @Part("Part One")
    fun partOne(input: BufferedReader) {

        val grid: MutableList<List<String>> = mutableListOf()

        input.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .forEach { line ->
                    run {
                        grid.add(line.trim().split("\\s+".toRegex()).map { it.trim() })
                    }
                }
        }

        var sum = solveProblem(grid, 0)
        for (i in 1..<grid[0].size) {
            sum += solveProblem(grid, i)
        }

        println(sum)
    }

    fun solveProblem(grid: List<List<String>>, index: Int): Long {
        val size = grid.size
        val operator = grid[size - 1][index].first()

        // Start at the first in the problem
        var sum = grid[0][index].toLong()
        for (i in 0..<size - 2) {
            val operand = grid[i + 1][index].toLong()
            sum = sum.math(operand, operator)
        }

        return sum;
    }

    @Part("Part Two")
    fun partTwo(input: BufferedReader) {
        var lines = input.readLines()

        val rawOps = lines[lines.size - 1]
        val operators = rawOps.split("\\s+".toRegex()).map { it.trim().first() }
        val wordIndexes = parseWordIndexes(rawOps)

        val biggestSize = lines.map { it.length }.maxOf { it }
        lines = lines.map { it.padEnd(biggestSize, ' ') }


        var sum = 0L
        operators.forEachIndexed { index, operator ->
            val problem = convertProblemToCephalopodFormat(wordIndexes, lines, index)
            val solved = solveProblem(problem, operator)
            sum += solved
        }
        println(sum)
    }

    fun solveProblem(problem: List<Long>, operator: Char): Long {

        var sum = problem.first()
        for (i in 1..<problem.size) {
            sum = sum.math(problem[i], operator)
        }

        return sum
    }

    fun convertProblemToCephalopodFormat(indexes: List<Int>, lines: List<String>, column: Int): List<Long> {
        val problem = lines.dropLast(1).map { line ->
            readNumber(line, indexes, column)
        }
        val worldLength = problem[0].length - 1

        val convertedProblem = mutableListOf<Long>()
        for (i in worldLength downTo 0) {
            val raw = StringBuilder()
            for (word in problem) {
                raw.append(word[i])
            }
            convertedProblem.add(raw.toString().trim().toLong())
        }

        return convertedProblem;
    }


    // Parse the char indexes of the operators
    fun parseWordIndexes(operators: String): MutableList<Int> {
        val charIndexes = mutableListOf<Int>()
        operators.forEachIndexed { index, c ->
            if (!c.isWhitespace()) charIndexes.add(index)
        }
        return charIndexes;
    }

    fun readNumber(line: String, indexes: List<Int>, col: Int): String {
        if (col == indexes.size - 1) return line.substring(indexes[col])
        return line.substring(indexes[col], indexes[col + 1] - 1)
    }


    fun Long.math(operand: Long, operator: Char): Long {
        return when (operator) {
            '*' -> this.times(operand)
            '+' -> this.plus(operand)
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }
}