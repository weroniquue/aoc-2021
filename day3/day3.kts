import java.io.File

val input = File("./input3.txt").readLines()
    .map { it.split("").filter { it.isNotEmpty() }.map { it.toInt() } }

fun countZerosAndOnes(position: Int, list: List<List<Int>>): Map<Int, Int> {
    return list.map { it[position] }.groupBy { it }.mapValues { it.value.size }
}

fun mostCommonValue(counted: Map<Int, Int>): Int {
    return if (counted[0]!! == counted[1]!!) {
        1
    } else if (counted[0]!! > counted[1]!!){
        0
    } else {
        1
    }
}

fun leastCommonValue(counted: Map<Int, Int>): Int {
    return if (counted[0]!! == counted[1]!!) {
        0
    } else if (counted[0]!! < counted[1]!!){
        0
    } else {
        1
    }
}

fun toDigit(binaryList: List<Int>): Int {
    return Integer.valueOf(binaryList.joinToString(""), 2)
}

var gammaRate = mutableListOf<Int>()
var epsilonRate = mutableListOf<Int>()

for (i in input[0].indices) {
    val counted = countZerosAndOnes(i, input)
    gammaRate.add(mostCommonValue(counted))
    epsilonRate.add(leastCommonValue(counted))
}
println(toDigit(gammaRate) * toDigit(epsilonRate))


var oxygenRate = input
var co2Rate = input

for (i in input[0].indices) {
    if (oxygenRate.size != 1) {
        val counted = countZerosAndOnes(i, oxygenRate)
        oxygenRate = oxygenRate.filter { it[i] == mostCommonValue(counted) }
    }
}

for (i in input[0].indices) {
    if (co2Rate.size != 1) {
        val counted = countZerosAndOnes(i, co2Rate)
        co2Rate = co2Rate.filter { it[i] == leastCommonValue(counted) }
    }
}

println(toDigit(oxygenRate[0]) * toDigit(co2Rate[0]))
