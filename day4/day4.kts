import java.io.File

val input = File("./input4.txt").readLines()

val numbers = input[0].split(",").map { it.toInt() }

data class Field(
    val number: Int,
    var marked: Boolean = false
)

data class Matrix(
    val fields: List<List<Field>>,
    val index: Int,
    var winner: Boolean,
    var winAfter: Int?
) {
    fun checkBingo(): Boolean {
        return fields.any { it.all { it.marked } } || bingoInColumn()
    }

    fun sumUnmarked() : Int {
        return fields.flatten().filter { !it.marked }.sumOf { it.number }
    }

    private fun bingoInColumn(): Boolean {
        for (i in fields.indices) {
            val bingo = fields.all { it[i].marked }
            if (bingo) {
                return true
            }
        }
        return false
    }
}

fun createFields(list: List<String>): List<List<Field>> {
    return list.map {
        it.split(" ")
            .filter { it.isNotEmpty() }
            .map { Field(it.toInt(), false) }
    }
}

fun createMatrix(index: Int, list: List<String>): Matrix {
    return Matrix(createFields(list), index, false, null)
}

fun markField(number: Int, matrix: Matrix) {
    matrix.fields.map { it.find { it.number == number }?.let { it.marked = true }}
}

val boards = input.drop(2).filter { it.isNotEmpty() }.chunked(5).mapIndexed { index, list -> createMatrix(index, list) }

var winner: Matrix? = null

for (number in numbers) {
    boards.forEach {
        markField(number, it)
        if (it.checkBingo()) {
            it.winner = true
            it.winAfter = number
            winner = it
            return@forEach
        }
    }
    if (winner != null ){
        break
    }
}
println(winner!!.sumUnmarked() * winner!!.winAfter!!)

for (number in numbers) {
    boards.filter { !it.winner }.forEach {
        markField(number, it)
        if (it.checkBingo()) {
            it.winner = true
            it.winAfter = number
        }
    }
}

numbers.reversed().forEach { number ->
    val win = boards.find { it.winAfter == number && it.winner}
    if (win != null ){
        println(win.sumUnmarked() * win.winAfter!!)
    }
}

