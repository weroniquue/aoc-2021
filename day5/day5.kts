import java.io.File
import kotlin.math.abs

data class Point(
    val x: Int,
    val y: Int
)

data class Line(
    val startPos: Point,
    val endPos: Point
) {
    fun isHorizontal(): Boolean {
        return startPos.y == endPos.y
    }

    fun isVertical(): Boolean {
        return startPos.x == endPos.x
    }

    fun isDiagonal(): Boolean {
        return Math.abs(startPos.x - endPos.x) == Math.abs(startPos.y - endPos.y)
    }

    fun maxXPosition(): Int {
        return maxOf(startPos.x, endPos.x)
    }

    fun maxYPosition(): Int {
        return maxOf(startPos.y, endPos.y)
    }

    fun markMatrix(m: Array<Array<Int>>) {
        if (this.isVertical()) {
            markVertical(m)
        } else if (this.isHorizontal()) {
            markHorizontalLine(m)
        } else if (this.isDiagonal()){
            var p1: Point
            var p2: Point

            if (startPos.x < endPos.x) {
                p1 = startPos
                p2 = endPos
            } else {
                p1 = endPos
                p2 = startPos
            }

            val direction = if (p2.y- p1.y > 0) {
                1
            } else if (p2.y == p1.y) {
                0
            } else {
                -1
            }

            val b = p1.y - direction * p1.x

            for (i in minOf(startPos.x, endPos.x)..maxOf(startPos.x, endPos.x)) {
                val y = direction * i + b
                m[y][i] +=1
            }
        }
    }

    private fun markHorizontalLine(m: Array<Array<Int>>) {
        for (i in minOf(startPos.x, endPos.x)..maxOf(startPos.x, endPos.x)) {
            m[startPos.y][i] += 1
        }
    }

    private fun markVertical(m: Array<Array<Int>>) {
        for (i in minOf(startPos.y, endPos.y)..maxOf(startPos.y, endPos.y)) {
            m[i][startPos.x] += 1
        }
    }
}

fun createLine(line: String): Line {
    val pos = line.split(" -> ")
        .flatMap { it.split(",") }
        .map { it.toInt() }
    return Line(Point(pos[0], pos[1]), Point(pos[2], pos[3]))
}

val input = File("./input5.txt")
    .readLines()
    .map { createLine(it) }

//val text = """0,9 -> 5,9
//8,0 -> 0,8
//9,4 -> 3,4
//2,2 -> 2,1
//7,0 -> 7,4
//6,4 -> 2,0
//0,9 -> 2,9
//3,4 -> 1,4
//0,0 -> 8,8
//5,5 -> 8,2"""
//
//val input = text.split("\n")
//    .map { createLine(it) }

val maxXPosition = input.map { it.maxXPosition() }.maxOrNull() ?: 0
val maxYPosition = input.map { it.maxYPosition() }.maxOrNull() ?: 0

var matrix = arrayOf<Array<Int>>()
for (i in 0..maxYPosition) {
    var array = arrayOf<Int>()
    for (j in 0..maxXPosition) {
        array += 0
    }
    matrix += array
}

input.forEach {
    it.markMatrix(matrix)
}
println(matrix.flatten().filter { it > 1 }.size)
