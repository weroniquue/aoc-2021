import java.io.File

val input = File("./input2.txt").readLines()
    .map { it.split(" ") }

val grouped = input.groupBy { it[0] }

val forward = grouped
    .getOrDefault("forward", listOf())
    .sumOf { it[1].toInt() }

val down = grouped
    .getOrDefault("down", listOf())
    .sumOf { it[1].toInt() }

val up = grouped
    .getOrDefault("up", listOf())
    .sumOf { it[1].toInt() }
println(forward * (down-up))

var x = 0
var aim = 0
var depth = 0

input.forEach {
    when(it[0]) {
        "down" -> aim += it[1].toInt()
        "up" -> aim -= it[1].toInt()
        "forward" -> { x += it[1].toInt()
            depth += aim*it[1].toInt()}
    }
}
println(x * depth)
