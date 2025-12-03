package days

import Part
import java.io.BufferedReader

class Day2 {


    fun Long.isRepeating(): Boolean {
        val s = this.toString();

        if (s.length % 2 != 0) return false;

        val mid = s.length / 2
        val firstHalf = s.take(mid)
        val secondHalf = s.substring(mid)
        return firstHalf == secondHalf
    }


    fun Long.isRepeatingComplex(): Boolean {
        val string = this.toString();
        val len = string.length

        // Need at least 2 chars to repeat
        if (len < 2) return false;

        for (subLen in 1..len / 2) {
            if (len % subLen != 0) continue

            val chunk = string.take(subLen)
            var i = subLen
            var matches = true

            while (i < len) {
                if (!string.regionMatches(i, chunk, 0, subLen)) {
                    matches = false
                    break
                }
                i += subLen
            }

            if (matches) {
                val count = len / subLen
                if (count >= 2) {
                    return true
                }
            }
        }
        return false;
    }


    fun parseRanges(input: BufferedReader): List<LongRange> {
        return input.readLine().split(',').map {
            val split = it.split('-')
            LongRange(split[0].toLong(), split[1].toLong())
        }
    }

    @Part("Part 1")
    fun partOne(input: BufferedReader) {
        val ranges = parseRanges(input)

        var invalidSum: Long = 0

        for (range in ranges) {
            for (n in range) {
                if (n.isRepeating()) invalidSum += n
            }
        }

        print(invalidSum)
    }

    @Part("Part 2")
    fun partTwo(input: BufferedReader) {
        val ranges = parseRanges(input)

        var invalidSum: Long = 0
        for (range in ranges) {
            for (n in range) {
                if (n.isRepeatingComplex()) invalidSum += n
            }
        }

        print(invalidSum)
    }

}