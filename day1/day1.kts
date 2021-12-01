import java.io.File

val input = File("input-day1.txt").useLines { it.toList() }.map { it.toInt() }

val result1 = input.zipWithNext().map{
    if (it.first < it.second) {
        1
    } else {
        -1
    }
}.filter { it == 1 }.size
println(result1)


val result2 = input.windowed(3, partialWindows = false)
    .map { it.sum() }
    .zipWithNext().map{
        if (it.first < it.second) {
            1
        } else {
            -1
        }
    }.filter { it == 1 }.size

println(result2)
